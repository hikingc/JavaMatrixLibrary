package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import io.github.hikingc.matrixsdk.api.well_known.PolicyServerInformation;
import io.github.hikingc.matrixsdk.api.well_known.ServerSupportInformation;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Class responsible for handling `/.well-known` endpoints, this is the recommended starting point
/// of the library.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#server-discovery">Matrix
///   Client-Server API Specification for Server Discovery</a>
@NullMarked
public class MatrixDiscovery {

  private final URI baseUrl;
  private final HttpTransport httpTransport;

  /// Create an instance of the class.
  ///
  /// @param baseUrl the base URL of the user, for example: `https://kde.org`.
  /// @param httpClient if supplied, the [HttpClient] that the library will use to perform calls,
  ///   otherwise the library will create one.
  public MatrixDiscovery(URI baseUrl, @Nullable HttpClient httpClient) {
    Objects.requireNonNull(baseUrl, "baseUrl must not be null.");
    this.baseUrl = baseUrl;
    this.httpTransport = new HttpTransport(httpClient);
  }

  /// Retrieve the `.well-known/matrix/client` data. This is the info you need to call before
  /// authenticating and performing any kind of operation to the rest of the lib.
  ///
  /// @apiNote this method strips `homeserver().baseUrl()` trailing slashes to remove any
  ///   inconsistency.
  ///
  /// @return a [DomainInformation] record object with data.
  /// @throws MatrixException when the payload cannot be processed
  public DomainInformation fetchWellKnown() {
    try {
      URI uri = URI.create(baseUrl + "/.well-known/matrix/client");
      var response = httpTransport.getRequest(uri, null);
      var result = Mapper.getObjectFromInputStream(response, DomainInformation.class);
      // It is important to note that the base_url value might include a trailing /. Consumers
      // should be prepared to handle both cases. - Matrix spec
      if (result.homeserver().baseUrl().endsWith("/")) {
        var modifiedUrl =
            result.homeserver().baseUrl().substring(0, result.homeserver().baseUrl().length() - 1);
        return new DomainInformation(
            new DomainInformation.HomeserverInfo(modifiedUrl),
            result.identityServer(),
            result.rtcFoci());
      }
      return result;
    } catch (MatrixException e) {
      throw new MatrixException("Failed to retrieve Matrix Domain Information.", e);
    }
  }

  /// Retrieve the `.well-known/matrix/support` data.
  ///
  /// @return a [ServerSupportInformation] record object with data.
  /// @throws MatrixException when the payload cannot be processed
  public ServerSupportInformation fetchServerSupport() {
    try {
      URI uri = URI.create(baseUrl + "/.well-known/matrix/client");
      var response = httpTransport.getRequest(uri, null);
      return Mapper.getObjectFromInputStream(response, ServerSupportInformation.class);
    } catch (MatrixException e) {
      throw new MatrixException("Failed to retrieve Matrix Server Support Information.", e);
    }
  }

  /// Retrieve the `.well-known/matrix/support` data.
  ///
  /// @return a [ServerSupportInformation] record object with data.
  /// @throws MatrixException when the payload cannot be processed
  public PolicyServerInformation fetchPolicyServer() {
    try {
      URI uri = URI.create(baseUrl + "/.well-known/matrix/client");
      var response = httpTransport.getRequest(uri, null);
      return Mapper.getObjectFromInputStream(response, PolicyServerInformation.class);
    } catch (MatrixException e) {
      throw new MatrixException("Failed to retrieve Matrix Policy Server Information.", e);
    }
  }
}
