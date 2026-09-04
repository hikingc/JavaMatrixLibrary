package io.github.hikingc.matrixsdk.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.auth.BrowserLauncher;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class MatrixOAuthLoginTest {

  private static MatrixAuth matrixAuth;
  private static String baseUrl;
  private int callbackPort;
  private TokenMetadata tokens = new TokenMetadata("ABCD", null, null, null, null);

  @BeforeEach
  void setupAuth(WireMockRuntimeInfo wireMockRuntimeInfo) throws IOException {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .willReturn(
                okJson(
                    """
                                        {
                                          "contacts": [
                                            {
                                              "email_address": "admin@example.org",
                                              "matrix_id": "@admin:example.org",
                                              "role": "m.role.admin"
                                            },
                                            {
                                              "email_address": "security@example.org",
                                              "role": "m.role.security"
                                            }
                                          ],
                                          "support_page": "https://example.org/support.html"
                                        }

                                        """)));
    stubFor(
        get(urlEqualTo("/_matrix/client/versions"))
            .willReturn(
                okJson(
                    """
                                            {
                                              "unstable_features": {
                                                "org.example.my_feature": true
                                              },
                                              "versions": [
                                                "r0.0.1",
                                                "v1.1"
                                              ]
                                            }
                                            """)));
    baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .willReturn(
                okJson(
                    """
                                                    {"m.homeserver": {"base_url": "%s"}}
                                                    """
                        .formatted(baseUrl))));
    matrixAuth = new MatrixAuth(URI.create(baseUrl), HttpClient.newBuilder().build());
    callbackPort = findFreePort();

    stubFor(
        get(urlEqualTo("/_matrix/client/v1/auth_metadata"))
            .willReturn(
                okJson(
                    """
                                    {
                                      "issuer": "%1$s/",
                                      "authorization_endpoint": "%1$s/oauth2/auth",
                                      "token_endpoint": "%1$s/oauth2/token",
                                      "registration_endpoint": "%1$s/oauth2/clients/register",
                                      "revocation_endpoint": "%1$s/oauth2/revoke",
                                      "grant_types_supported": ["authorization_code", "refresh_token"],
                                      "response_types_supported": ["code"],
                                      "response_modes_supported": ["query", "fragment"],
                                      "code_challenge_methods_supported": ["S256"]
                                    }
                                    """
                        .formatted(baseUrl))));

    stubFor(
        post(urlEqualTo("/oauth2/clients/register"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
"""
{"client_id": "test-client-id"}""")));
  }

  @Test
  void performOAuthLogin_happyPath_returnsTokens() {
    stubFor(
        post(urlEqualTo("/oauth2/token"))
            .willReturn(
                okJson(
                    """
                                    {
                                      "access_token": "syt_test_token",
                                      "token_type": "Bearer",
                                      "expires_in": 300,
                                      "refresh_token": "test_refresh",
                                      "scope": "urn:matrix:client:api:*"
                                    }
                                    """)));

    AtomicReference<String> capturedCodeChallenge = new AtomicReference<>();
    BrowserLauncher fakeBrowser =
        fakeBrowserCompleting(callbackPort, "fake-auth-code", capturedCodeChallenge);

    TokenMetadata token =
        matrixAuth.performOAuthLogin("TestClient", callbackPort, "TESTDEVICE01", fakeBrowser);

    assertThat(token.accessToken()).isEqualTo("syt_test_token");
    assertThat(token.refreshToken()).isEqualTo("test_refresh");

    verify(
        postRequestedFor(urlEqualTo("/oauth2/token"))
            .withRequestBody(containing("grant_type=authorization_code"))
            .withRequestBody(containing("code=fake-auth-code"))
            .withRequestBody(containing("client_id=test-client-id")));
  }

  @Test
  void performOAuthLogin_verifiesPkceChallengeMatchesVerifier() throws Exception {
    stubFor(
        post(urlEqualTo("/oauth2/token"))
            .willReturn(
                okJson(
                    """
                                    {
                                      "access_token": "syt_test_token",
                                      "token_type": "Bearer",
                                      "expires_in": 300,
                                      "refresh_token": "test_refresh",
                                      "scope": "urn:matrix:client:api:*"
                                    }
                                    """)));

    AtomicReference<String> capturedCodeChallenge = new AtomicReference<>();
    BrowserLauncher fakeBrowser =
        fakeBrowserCompleting(callbackPort, "fake-auth-code", capturedCodeChallenge);

    matrixAuth.performOAuthLogin("TestClient", callbackPort, "TESTDEVICE01", fakeBrowser);

    String sentVerifier = extractFormParam(lastTokenRequestBody(), "code_verifier");
    String expectedChallenge = sha256Base64Url(sentVerifier);

    assertThat(capturedCodeChallenge.get())
        .as(
            "code_challenge sent in auth URI should equal SHA256(code_verifier) sent to token endpoint")
        .isEqualTo(expectedChallenge);
  }

  @Test
  void performOAuthLogin_stateMismatch_throwsMatrixException() {
    BrowserLauncher maliciousBrowser =
        uri -> sendCallback(callbackPort, "state=not-the-real-state&code=whatever");

    assertThatThrownBy(
            () ->
                matrixAuth.performOAuthLogin(
                    "TestClient", callbackPort, "TESTDEVICE01", maliciousBrowser))
        .isInstanceOf(MatrixException.class)
        .hasMessageContaining("A fatal error has ceased authorization flow.");
  }

  @Test
  void performOAuthLogin_authorizationDenied_throwsMatrixException() {
    BrowserLauncher denyingBrowser =
        uri -> {
          String state = extractQueryParam(uri.getRawQuery(), "state");
          sendCallback(
              callbackPort,
              "state=" + state + "&error=access_denied&error_description=User+declined");
        };

    assertThatThrownBy(
            () ->
                matrixAuth.performOAuthLogin(
                    "TestClient", callbackPort, "TESTDEVICE01", denyingBrowser))
        .isInstanceOf(MatrixException.class)
        .hasMessageContaining("A fatal error has ceased authorization flow.");
  }

  // ---------------------------------------------------------------------
  // Helpers
  // ---------------------------------------------------------------------

  private BrowserLauncher fakeBrowserCompleting(
      int port, String fakeCode, AtomicReference<String> capturedCodeChallenge) {
    return uri -> {
      String query = uri.getRawQuery();
      String state = extractQueryParam(query, "state");
      capturedCodeChallenge.set(extractQueryParam(query, "code_challenge"));
      sendCallback(port, "state=" + state + "&code=" + fakeCode);
    };
  }

  private static void sendCallback(int port, String rawQuery) {
    try {
      HttpClient.newHttpClient()
          .send(
              HttpRequest.newBuilder(
                      URI.create("http://127.0.0.1:" + port + "/callback?" + rawQuery))
                  .GET()
                  .build(),
              HttpResponse.BodyHandlers.discarding());
    } catch (Exception e) {
      throw new RuntimeException("Fake browser failed to hit callback", e);
    }
  }

  private static int findFreePort() throws IOException {
    try (ServerSocket socket = new ServerSocket(0)) {
      return socket.getLocalPort();
    }
  }

  private static String extractQueryParam(String query, String key) {
    if (query == null) return null;
    return Arrays.stream(query.split("&"))
        .map(pair -> pair.split("=", 2))
        .filter(kv -> kv[0].equals(key))
        .map(kv -> kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "")
        .findFirst()
        .orElse(null);
  }

  private static String extractFormParam(String body, String key) {
    return extractQueryParam(body, key);
  }

  private String lastTokenRequestBody() {
    return findAll(postRequestedFor(urlEqualTo("/oauth2/token"))).getFirst().getBodyAsString();
  }

  private static String sha256Base64Url(String input) throws Exception {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
    return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
  }

  @Test
  void getCurrentAccountInformation_WithACorrectPayload_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/_matrix/client/v3/account/whoami"))
            .withHeader("Authorization", matching("Bearer .*"))
            .willReturn(
                okJson(
                    """
                        {
                          "device_id": "ABC1234",
                          "user_id": "@joe:example.org"
                        }
                        """)));
    var response = matrixAuth.getCurrentAccountInformation("token");
    assertThat(response).isNotNull();
  }

  @Test
  void getFetchWellKnown() {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .withHeader("Authorization", containing("Bearer .*"))
            .willReturn(
                okJson(
                    """
                        {
                          "contacts": [
                            {
                              "email_address": "admin@example.org",
                              "matrix_id": "@admin:example.org",
                              "role": "m.role.admin"
                            },
                            {
                              "email_address": "security@example.org",
                              "role": "m.role.security"
                            }
                          ],
                          "support_page": "https://example.org/support.html"
                        }

                        """)));
    var response = matrixAuth.fetchWellKnown();
    assertThat(response).isNotNull();
  }

  @Test
  void getVersions_WithACorrectPayload_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/_matrix/client/versions"))
            .withHeader("Authorization", matching("Bearer .*"))
            .willReturn(
                okJson(
                    """
                            {
                              "unstable_features": {
                                "org.example.my_feature": true
                              },
                              "versions": [
                                "r0.0.1",
                                "v1.1"
                              ]
                            }
                            """)));
    assertThat(tokens.accessToken()).isNotNull();
    var response = matrixAuth.getVersions(tokens.accessToken());
    assertThat(response).isNotNull();
  }
}
