package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.auth.WhoAmI;
import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;

/// Core interface for executing protocol operations to authenticate and server discovery.
///
/// @apiNote This is a Work-In-Progress interface
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#client-authentication">Matrix
///   Client-Server API Specification for Authentication</a>
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#server-discovery">Matrix
///   Client-Server API Specification for Server Discovery</a>
public interface Auth {

  /// Check information about the owner of a given access token.
  ///
  /// @param token a [TokenMetadata#accessToken()] token.
  /// @return a [WhoAmI] object if the token belongs to someone
  /// @throws MatrixIOException when the payload cannot be processed
  WhoAmI getCurrentAccountInformation(String token);

  /// Method used to obtain the .well-known data and store the base url.
  ///
  /// @return a [DiscoveryResponse] with data.
  /// @throws IllegalArgumentException when the homeserver url violates RFC 2396 or is null
  /// @throws MatrixIOException when the payload cannot be processed
  DiscoveryResponse fetchWellKnown();
}
