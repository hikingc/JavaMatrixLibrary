package io.github.hikingc.matrixsdk.services.utils;

import io.github.hikingc.matrixsdk.exceptions.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// [HttpTransport] handles all network-related tasks shared across the library, including issuing
/// requests, processing responses, and URI encoding.
///
/// Unless otherwise documented, requests expect JSON bodies as [String]s and return unprocessed
/// response bodies as [String]s, callers are responsible for their own (de)serialization.
///
/// Failed requests are validated against the server's response and throw [MatrixApiException],
/// populated with the HTTP status code and any error message returned by the server, and
/// [MatrixIOException] if the server JSON response wasn't even sent.
@NullMarked
public class HttpTransport {
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private final HttpClient client;

  private final Logger logger = LoggerFactory.getLogger(HttpTransport.class);

  /// Constructor to initialize the HTTP Client.
  ///
  /// @param httpClient a valid [HttpClient].
  public HttpTransport(HttpClient httpClient) {
    client = httpClient;
  }

  /// Handles return code validation from Matrix servers.
  ///
  /// @param response the client response.
  /// @throws MatrixApiException when the server responds with an unsuccessful HTTP Code.
  private void validateResponse(InputStream response, int code, HttpHeaders headers) {
    logger.debug(
        "Validating response code: {}, headers: {}, for body: {}", code, headers, response);
    if (code >= 200 && code < 300) {
      return;
    }

    ErrorResponse errorResponse;
    try {
      errorResponse = Mapper.getObjectFromString(response, ErrorResponse.class);
    } catch (MatrixSerializationException e) {
      throw new MatrixApiException(
          "Server returned unparseable error body, HTTP code: " + code, code, e);
    }

    var retryAfter = headers.firstValue("Retry-After");
    if (retryAfter.isPresent()) {
      try {
        int retryAfterSeconds = Integer.parseInt(retryAfter.get().trim());
        errorResponse =
            new ErrorResponse(errorResponse.errCode(), errorResponse.error(), retryAfterSeconds);
      } catch (NumberFormatException _) {
        logger.debug("Retry-After header was not a valid integer: {}", retryAfter.get());
      }
    }

    throw MatrixApiException.fromErrorResponse(code, errorResponse);
  }

  /// Sends a `GET` request to the given endpoint.
  ///
  /// @param path the [URI] of the endpoint to `GET`.
  /// @param authToken if supplied, the `Bearer` token.
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while sending the request.
  /// @throws MatrixInterruptedException if the operation has been interrupted.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream getRequest(URI path, @Nullable String authToken) {
    logger.trace("Get HTTP Request for path: {}, authToken: {}", path, authToken);
    var builderRequest =
        HttpRequest.newBuilder()
            .uri(path)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header("Accept-Encoding", "gzip")
            .GET();
    if (authToken != null) {
      builderRequest.header(AUTHORIZATION, BEARER + authToken);
    }
    var request = builderRequest.build();
    HttpResponse<InputStream> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// Sends a `POST` request to the given endpoint.
  ///
  /// @param path the [URI] of the endpoint to `POST`.
  /// @param body a JSON [String].
  /// @param authToken if supplied, the `Bearer` token.
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while processing the request.
  /// @throws MatrixInterruptedException if the operation has been interrupted.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream postRequest(URI path, byte @Nullable [] body, @Nullable String authToken) {
    logger.trace("Post HTTP Request for path: {}, authToken: {}", path, authToken);
    var builderRequest = HttpRequest.newBuilder().header("Accept-Encoding", "gzip").uri(path);
    if (authToken != null) {
      builderRequest.header(AUTHORIZATION, BEARER + authToken);
    }

    if (body != null) {
      builderRequest.header(CONTENT_TYPE, APPLICATION_JSON);
    }

    builderRequest.POST(
        body != null
            ? HttpRequest.BodyPublishers.ofByteArray(body)
            : HttpRequest.BodyPublishers.noBody());

    var request = builderRequest.build();

    HttpResponse<InputStream> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// Sends a `POST` request to the given endpoint.
  ///
  /// This endpoint is exclusively used for Authentication workflows with OAuth 2.0.
  ///
  /// @param path the [URI] of the endpoint to query.
  /// @param body a JSON [String].
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while sending the request.
  /// @throws MatrixInterruptedException if the operation has been interrupted or a server returned
  ///   with unsuccessful HTTP Code.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream postAuth(URI path, @Nullable String body) {
    logger.trace("Post [Auth] HTTP Request for path: {}, body: {}", path, body);
    var builderRequest = HttpRequest.newBuilder().header("Accept-Encoding", "gzip").uri(path);

    builderRequest.header(CONTENT_TYPE, "application/x-www-form-urlencoded");

    builderRequest.POST(
        body != null
            ? HttpRequest.BodyPublishers.ofString(body)
            : HttpRequest.BodyPublishers.noBody());

    var request = builderRequest.build();

    HttpResponse<InputStream> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// Sends a `PUT` request to the given endpoint.
  ///
  /// @param path the [URI] of the endpoint to query.
  /// @param body a JSON [String]
  /// @param authToken if supplied, the `Bearer` token.
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while sending the request.
  /// @throws MatrixInterruptedException if the operation has been interrupted or a server returned
  ///   with unsuccessful HTTP Code.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream putRequest(URI path, byte @Nullable [] body, String authToken) {
    logger.trace("Put HTTP Request for path: {}, body: {}", path, body);
    var builderRequest =
        HttpRequest.newBuilder()
            .header("Accept-Encoding", "gzip")
            .uri(path)
            .headers(AUTHORIZATION, BEARER + authToken, CONTENT_TYPE, APPLICATION_JSON);

    builderRequest.PUT(
        body != null
            ? HttpRequest.BodyPublishers.ofByteArray(body)
            : HttpRequest.BodyPublishers.noBody());
    var request = builderRequest.build();

    HttpResponse<InputStream> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// Sends a `PUT` request to the given endpoint to upload a resource.
  ///
  /// The [#CONTENT_TYPE] will be generated using [Files#probeContentType(Path)]
  ///
  /// @param path the [URI] of the endpoint to query.
  /// @param resource a [Path] pointing to the resource to be uploaded.
  /// @param authToken if supplied, the `Bearer` token.
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while sending the request
  /// @throws MatrixInterruptedException if the operation has been interrupted or a server returned
  ///   with unsuccessful HTTP Code.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream putResource(URI path, Path resource, String authToken) {
    logger.trace("Put HTTP Resource for path: {}, authToken: {}", path, authToken);
    HttpRequest uploadRequest;
    try {
      uploadRequest =
          HttpRequest.newBuilder()
              .header("Accept-Encoding", "gzip")
              .uri(path)
              .headers(
                  AUTHORIZATION, BEARER + authToken, CONTENT_TYPE, Files.probeContentType(resource))
              .PUT(HttpRequest.BodyPublishers.ofFile(resource))
              .build();
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    }

    HttpResponse<InputStream> response;
    try {
      response = client.send(uploadRequest, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// Sends a `DELETE` request to the given endpoint.
  ///
  /// @param path the [URI] of the endpoint to query.
  /// @param authToken if supplied, the `Bearer` token.
  /// @return an [InputStream] to process as JSON.
  /// @throws MatrixIOException if an I/O error has occurred while sending the request.
  /// @throws MatrixInterruptedException if the operation has been interrupted or a server returned
  ///   with unsuccessful HTTP Code.
  /// @throws MatrixApiException when the response from the server is not successful.
  public InputStream deleteRequest(URI path, String authToken) {
    logger.trace("Delete HTTP Request for path: {}, authToken: {}", path, authToken);
    HttpRequest deleteRequest =
        HttpRequest.newBuilder()
            .header("Accept-Encoding", "gzip")
            .uri(path)
            .header(AUTHORIZATION, BEARER + authToken)
            .DELETE()
            .build();

    HttpResponse<InputStream> response;
    try {
      response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofInputStream());
    } catch (IOException e) {
      throw new MatrixIOException(
          "There has been an I/O error attempting to process this request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new MatrixInterruptedException("This request has been interrupted", e);
    }
    var uncResponse = decompressIfNeeded(response);
    this.validateResponse(uncResponse, response.statusCode(), response.headers());
    return uncResponse;
  }

  /// URL-encodes a string using UTF-8.
  ///
  /// @param value the string to encode.
  /// @return the URL-encoded string.
  private String encode(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }

  /// Builds a [URI] from a base, path, and query parameters, with the path and query
  /// percent-encoded to UTF-8.
  ///
  /// @param baseUrl the base [URI] containing a schema and an authority.
  /// @param path the path, for example: `/_matrix/client/v3/join/!room:example.org`
  /// @param params query parameters; accepts wrapped primitives and Lists for repeated parameters.
  ///   Null values, null list items, or a null/empty map are all safely ignored.
  /// @return a safe, fully composed [URI].
  public URI generateEncodedURI(String baseUrl, String path, @Nullable Map<String, Object> params) {
    String query = encodeQueryParams(params);
    try {
      URI base = URI.create(baseUrl);
      return new URI(
          base.getScheme(), base.getAuthority(), path, query.isEmpty() ? null : query, null);
    } catch (URISyntaxException e) {
      throw new MatrixException("Failure parsing URI", e);
    }
  }

  /// Method to check if the server did return response as GZIP and then decompress it.
  ///
  /// @param response an HTTP Response.
  /// @return a `byte` array.
  /// @throws MatrixIOException if an I/O exception has occurred.
  private InputStream decompressIfNeeded(HttpResponse<InputStream> response) {
    logger.trace(
        "Decompress HTTP Response for path: {}, body: {}", response.statusCode(), response.body());
    String encoding = response.headers().firstValue("Content-Encoding").orElse("");
    if (!encoding.equalsIgnoreCase("gzip")) {
      return response.body();
    }
    try {
      logger.debug("Server has returned with GZIP compression. Decompressing response");
      return new GZIPInputStream(response.body());
    } catch (IOException e) {
      throw new MatrixIOException("Failed to decompress gzip response body", e);
    }
  }

  /// Builds a [URI] from a base, path, and query parameters. This method WON'T encode to UTF-8 the
  /// queries
  ///
  /// @param baseUrl the base [URI] containing a schema and an authority.
  /// @param path the path, for example: `/_matrix/client/v3/join/!room:example.org`
  /// @param params query parameters; accepts wrapped primitives and Lists for repeated parameters.
  ///   Null values, null list items, or a null/empty map are all safely ignored.
  /// @return a safe, fully composed [URI].
  public URI generateRawURI(String baseUrl, String path, @Nullable Map<String, Object> params) {
    String query = rawQueryParams(params);
    try {
      URI base = URI.create(baseUrl);
      return new URI(
          base.getScheme(), base.getAuthority(), path, query.isEmpty() ? null : query, null);
    } catch (URISyntaxException e) {
      throw new MatrixException("Failure parsing URI", e);
    }
  }

  private String encodeQueryParams(@Nullable Map<String, @Nullable Object> params) {
    if (params == null || params.isEmpty()) return "";
    return params.entrySet().stream()
        .filter(e -> e.getValue() != null)
        .flatMap(
            e -> valuesOf(e.getValue()).map(v -> encode(e.getKey()) + "=" + encode(v.toString())))
        .collect(Collectors.joining("&"));
  }

  private String rawQueryParams(@Nullable Map<String, @Nullable Object> params) {
    if (params == null || params.isEmpty()) return "";
    return params.entrySet().stream()
        .filter(e -> e.getValue() != null)
        .flatMap(e -> valuesOf(e.getValue()).map(v -> e.getKey() + "=" + v.toString()))
        .collect(Collectors.joining("&"));
  }

  private Stream<?> valuesOf(Object value) {
    return value instanceof List<?> list
        ? list.stream().filter(Objects::nonNull)
        : Stream.of(value);
  }
}
