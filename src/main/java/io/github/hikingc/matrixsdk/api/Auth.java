package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.auth.Versions;
import io.github.hikingc.matrixsdk.api.auth.WhoAmI;
import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Core interface for executing protocol operations to authenticate and server discovery.
///
/// @apiNote This is a Work-In-Progress interface
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#client-authentication">Matrix
///   Client-Server API Specification for Authentication</a>
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#server-discovery">Matrix
///   Client-Server API Specification for Server Discovery</a>
@NullMarked
public interface Auth {

  /// Check information about the owner of a given access token.
  ///
  /// @param token a [io.github.hikingc.matrixsdk.api.auth.TokenMetadata#accessToken()] token.
  /// @return a [WhoAmI] object if the token belongs to someone
  /// @throws MatrixIOException when the payload cannot be processed
  WhoAmI getCurrentAccountInformation(String token);

  /// Method used to obtain the `.well-known/matrix/client` data.
  ///
  /// @return a [DiscoveryResponse] with data.
  /// @throws IllegalArgumentException when the homeserver url violates RFC 2396 or is null
  /// @throws MatrixIOException when the payload cannot be processed
  DiscoveryResponse fetchWellKnown();

  /// Checks what versions and unstable features are supported by the server.
  ///
  /// The server _may_ additionally advertise experimental features it supports through
  /// `unstable_features`. These features should be namespaced and may optionally include version
  /// information within their name if desired. Features listed here are not for optionally toggling
  /// parts of the Matrix specification and should only be used to advertise support for a feature
  /// which has not yet landed in the spec. For example, a feature currently undergoing the proposal
  /// process may appear here and eventually be taken off this list once the feature lands in the
  /// spec and the server deems it reasonable to do so.
  ///
  /// Servers can choose to enable some features only for some users, so clients should include
  /// authentication in the request to get all the features available for the logged-in user. **If no
  /// authentication is provided, the server should only return the features available to all users.
  /// Servers may wish to keep advertising features here after they’ve been released into the spec
  /// to give clients a chance to upgrade appropriately.**
  ///
  /// **Additionally, clients should avoid using unstable features in their stable releases.**
  ///
  /// @param token optional authentication token to hint servers that they should return features
  ///   that may be only for some users.
  /// @return supported versions and unstable features.
  Versions getVersions(@Nullable String token);
}
