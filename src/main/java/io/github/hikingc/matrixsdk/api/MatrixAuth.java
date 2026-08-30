package io.github.hikingc.matrixsdk.api;

import io.fusionauth.http.server.HTTPHandler;
import io.fusionauth.http.server.HTTPListenerConfiguration;
import io.fusionauth.http.server.HTTPServer;
import io.github.hikingc.matrixsdk.api.auth.AuthMetadata;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.auth.Versions;
import io.github.hikingc.matrixsdk.api.auth.WhoAmI;
import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import io.github.hikingc.matrixsdk.exceptions.ErrorResponse;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;

/// This class handles endpoints to retrieve essential data to operate with Matrix servers, it
/// provides a basic implementation of the OAuth 2.0 API, and additional methods to retrieve
/// metainformation such as server [URI]s and who's tokens are being held.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#oauth-20-api">Matrix
///   Client-Server API Specification for OAuth 2.0</a>
/// @see <a href="https://datatracker.ietf.org/doc/html/rfc6749">OAuth 2.0 specification</a>
@NullMarked
public class MatrixAuth implements Auth {

  private final Logger logger = LoggerFactory.getLogger(MatrixAuth.class);
  private final HttpTransport httpTransport;
  private final Random random = new SecureRandom();
  private final URI baseUrl;

  /// Constructor that instantiates the class.
  ///
  /// @param baseUrl the base [URI]
  /// @param httpClient an [HttpClient].
  public MatrixAuth(URI baseUrl, HttpClient httpClient) {
    this.baseUrl = baseUrl;
    this.httpTransport = new HttpTransport(httpClient);
  }

  private static String generateCodeChallenge(String codeVerifier) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new MatrixException("Error during code challenge generation.", e);
    }
    byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
    return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
  }

  /// Reads a callback response and extracts a key.
  ///
  /// @param url the callback url, separated by query parameters.
  /// @param key the key to be extracted.
  /// @return `null` if not found, otherwise the key value.
  @NullUnmarked
  private static String extractQueryParam(String url, String key) {
    if (url == null) {
      return null;
    }
    for (String pair : url.split("&")) {
      String[] kv = pair.split("=", 2);
      if (kv.length == 2 && kv[0].equals(key)) {
        return java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
      }
    }
    return null;
  }

  /// Get the OAuth 2.0 authorization server metadata, as defined in RFC 8414
  ///
  /// @return an [AuthMetadata] object.
  /// @throws MatrixIOException when the payload cannot be processed
  public AuthMetadata getAuthMetadata() {
    DiscoveryResponse discoveryResponse = this.fetchWellKnown();
    var uri =
        httpTransport.generateEncodedURI(
            discoveryResponse.homeserver().baseUrl(), "/_matrix/client/v1/auth_metadata", null);
    var responseBody = httpTransport.getRequest(uri, null);
    return Mapper.getObjectFromString(responseBody, AuthMetadata.class);
  }

  @Override
  public WhoAmI getCurrentAccountInformation(String token) {
    DiscoveryResponse discoveryResponse = this.fetchWellKnown();
    var response =
        httpTransport.getRequest(
            URI.create(
                discoveryResponse.homeserver().baseUrl() + "/_matrix/client/v3/account/whoami"),
            token);
    return Mapper.getObjectFromString(response, WhoAmI.class);
  }

  @Override
  public DiscoveryResponse fetchWellKnown() {
    try {
      URI uri = URI.create(baseUrl + "/.well-known/matrix/client");
      var response = httpTransport.getRequest(uri, null);
      return Mapper.getObjectFromString(response, DiscoveryResponse.class);
    } catch (JacksonException e) {
      throw new MatrixIOException("Failed to parse Matrix discovery JSON", e);
    }
  }

  /// Runs the full MSC2965/2966/2967 OAuth 2.0 flow: discovery, dynamic client registration, PKCE
  /// authorization via a loopback callback server, and token exchange.
  ///
  /// This flow is intended for native local clients that can invoke a browser and receive
  /// callbacks, it follows Matrix "authorization code flow".
  ///
  /// @param clientName the client name
  /// @param port the port connection
  /// @param deviceId the device id
  /// @return a [TokenMetadata] with all the necessary information about the tokens.
  /// @throws MatrixIOException when a network or parsing step fails.
  /// @throws MatrixException when the auth code is not supported by the server.
  public TokenMetadata performOAuthLogin(String clientName, int port, String deviceId) {
    // We get the auth metadata
    var metadata = this.getAuthMetadata();
    if (!metadata.grantTypesSupported().contains("authorization_code")) {
      throw new MatrixException("Authorization code is not supported.");
    }

    // Create our redirect
    String redirectUri = "http://127.0.0.1:" + port + "/callback";

    // Encode the endpoint parameters to register
    Map<String, Object> map = new HashMap<>();
    map.put("client_name", clientName);
    map.put("redirect_uris", List.of(redirectUri));
    map.put("grant_types", List.of("authorization_code", "refresh_token"));
    map.put("token_endpoint_auth_method", "none");
    map.put("application_type", "native");
    map.put("client_uri", "https://github.com/hikingc/JavaMatrixLibrary");
    var mappedInput = Mapper.createObjectFromMap(map);

    // Send the payload using the aforementioned record obtained and get the client_id
    var responseBody =
        httpTransport.postRequest(metadata.registrationEndpoint(), mappedInput, null);
    String responseBodyString = new String(responseBody);
    logger.info("Registration response: {}", responseBodyString);

    var clientId = Mapper.getStringValueOfAJsonKey(responseBody, "client_id");
    if (clientId.isBlank()) {
      throw new MatrixIOException(
          "Dynamic client registration failed or returned no client_id. Response: "
              + Arrays.toString(responseBody));
    }

    // Finish registering client, now we do the login flow

    // We generate values
    String codeVerifier = generateCodeVerifier();
    String codeChallenge = generateCodeChallenge(codeVerifier);
    String state = generateRandomUrlSafeString(24);
    // https://element-hq.github.io/matrix-authentication-service/reference/scopes.html#urnmatrixclientapi
    String scope = "urn:matrix:client:api:* urn:matrix:client:device:" + deviceId;

    Map<String, Object> mapAuth = new HashMap<>();
    mapAuth.put("response_type", "code"); // Always
    mapAuth.put("client_id", clientId);
    mapAuth.put("scope", scope);
    mapAuth.put("state", state);
    mapAuth.put(
        "response_mode", "query"); // It MUST be `query` to extract the values properly later.
    mapAuth.put("code_challenge", codeChallenge);
    mapAuth.put("code_challenge_method", "S256"); // Always
    // Send the payload, parameters are not encoded
    // https://spec.matrix.org/v1.19/client-server-api/#authorisation-code-flow
    var uriAuth =
        httpTransport.generateRawURI(
            metadata.authorizationEndpoint().toString(),
            metadata.authorizationEndpoint().getPath(),
            mapAuth); // We will use this for opening a browser

    CompletableFuture<String> authorizationCode = new CompletableFuture<>();

    // The http handler
    HTTPHandler handler =
        (req, res) -> {
          String query = req.getQueryString();
          logger.debug("Authorization code query: {}", query);
          String returnedState =
              extractQueryParam(query, "state"); // Returned regardless of success or failure
          String code = extractQueryParam(query, "code");
          String responseBodyCallback;

          if (code == null) {
            String error = extractQueryParam(query, "error");
            String errorDescription = extractQueryParam(query, "error_description");
            String errorUri = extractQueryParam(query, "error_uri");
            ErrorResponse response = new ErrorResponse(error, errorDescription, null);
            if (errorUri != null) {
              response =
                  new ErrorResponse(
                      error,
                      errorDescription + ", see:" + errorUri + " for more information.",
                      null);
            }
            authorizationCode.completeExceptionally(
                new MatrixException("Authorization failed: " + response));
            return;
          }

          // We validate that the state and code are received
          if (!state.equals(returnedState)) {
            responseBodyCallback = "State mismatch; possible CSRF, aborting.";
            authorizationCode.completeExceptionally(new MatrixException(responseBodyCallback));
            return;
          }
          // If all went well
          authorizationCode.complete(code);
          responseBodyCallback = "Login complete. You can close this tab and return to the app.";

          // After that we set the status as 200 and continue down the happy path
          byte[] bytes = responseBodyCallback.getBytes(StandardCharsets.UTF_8);
          res.setStatus(200);
          res.setContentLength(bytes.length);
          try (OutputStream os = res.getOutputStream()) {
            os.write(bytes);
          } catch (IOException e) {
            throw new MatrixException("Error writing to output stream", e);
          }
        };

    String code; // authorizationCode will bring us this.
    try (HTTPServer server =
        new HTTPServer()
            .withHandler(handler)
            .withListener(
                new HTTPListenerConfiguration(InetAddress.ofLiteral("127.0.0.1"), port))) {
      server.start();

      logger.debug("URI AUTH: {}", uriAuth);
      openBrowser(uriAuth);
      code = authorizationCode.get(5, TimeUnit.MINUTES); // Might modify later...?
    } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
      Thread.currentThread().interrupt();
      throw new MatrixException("A fatal error has ceased authorization flow.", e);
    }

    String tokenRequestBody =
        "grant_type=authorization_code"
            + "&code="
            + URLEncoder.encode(code, StandardCharsets.UTF_8)
            + "&redirect_uri="
            + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
            + "&client_id="
            + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
            + "&code_verifier="
            + URLEncoder.encode(codeVerifier, StandardCharsets.UTF_8);

    var tokenRes = httpTransport.postAuth(metadata.tokenEndpoint(), tokenRequestBody);

    return Mapper.getObjectFromString(tokenRes, TokenMetadata.class);
  }

  /// Attempts to retrieve new [TokenMetadata] by exchanging a refresh token for a new auth token.
  ///
  /// @param tokenMetadata either a previous [TokenMetadata] from a refresh or the data received
  ///   from [#performOAuthLogin(String, int, String)]
  /// @return a refreshed [TokenMetadata].
  /// @see <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-6">RFC 6749 section 6.</a>
  public TokenMetadata attemptRefreshToken(TokenMetadata tokenMetadata) {
    String refreshToken = tokenMetadata.refreshToken();
    var metadata = this.getAuthMetadata();
    if (!metadata.grantTypesSupported().contains("refresh_token")) {
      throw new MatrixException("Refresh token not supported");
    }
    String tokenRequestBody =
        "grant_type=refresh_token&refresh_token=%s"
            .formatted(URLEncoder.encode(refreshToken, StandardCharsets.UTF_8));
    var refreshRes = httpTransport.postAuth(metadata.tokenEndpoint(), tokenRequestBody);

    return Mapper.getObjectFromString(refreshRes, TokenMetadata.class);
  }

  @Override
  public Versions getVersions(@Nullable String authToken) {
    var wellKnown = fetchWellKnown();
    var response =
        httpTransport.getRequest(
            URI.create(wellKnown.homeserver().baseUrl() + "/_matrix/client/versions"), authToken);
    return Mapper.getObjectFromString(response, Versions.class);
  }

  private String generateCodeVerifier() {
    byte[] randomBytes = new byte[32];
    random.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }

  private String generateRandomUrlSafeString(int numBytes) {
    byte[] randomBytes = new byte[numBytes];
    random.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }

  private void openBrowser(URI url) throws IOException {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      Desktop.getDesktop().browse(url);
    } else {
      logger.warn("Could not auto-open a browser. Open this URL manually: {}", url);
    }
  }
}
