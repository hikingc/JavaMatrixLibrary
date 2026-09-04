package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.userdata.UserProfile;
import io.github.hikingc.matrixsdk.api.userdata.UsersFound;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import io.github.hikingc.matrixsdk.exceptions.MatrixInterruptedException;

/// Core interface for executing protocol operations against User data.
///
/// All operations in this interface are blocking. Implementations must ensure thread safety and
/// avoid synchronization blocks that cause carrier thread pinning during network I/O.
///
/// Unless otherwise noted, every method in this interface throws [MatrixIOException] if the request
/// or response payload cannot be processed, and [MatrixInterruptedException] if the server's response
/// status is not successful.
///
/// @see <a href="https://spec.matrix.org/v1.18/client-server-api/#user-data">Matrix Client-Server
///   API Specification for User Data</a>
public interface UserData {

  /// Perform a case-insensitive search of users based on a `search term`. Only users that are
  /// visible to the caller will be included in the search (are in `public` rooms with the caller,
  /// or `world_readable` for example).
  ///
  /// The search is **not collated to any language type**.
  ///
  /// @param limit the maximum number of results.
  /// @param searchTerm the term to search for.
  /// @return all the [UsersFound] by the server.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixInterruptedException when the client was interrupted.
  UsersFound searchUsersByTerm(Integer limit, String searchTerm);

  /// Get the profile of a user
  ///
  /// @param userId the [UserID] to target.
  /// @return its [UserProfile].
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixInterruptedException when the client was interrupted.
  UserProfile getUserProfile(UserID userId);

  /// Get the value of a profile field for a user
  ///
  /// @param userId the [UserID] to target.
  /// @param keyName the key name.
  /// @return the corresponding value of the pair.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixInterruptedException when the client was interrupted.
  String getUserProfileByProperty(
      UserID userId, String keyName); // only 1 property allowed so no Map

  /// Set or update a profile field for a user.
  ///
  /// @param userId the [UserID] to target.
  /// @param keyName the key name.
  /// @param valueName the value name.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixInterruptedException when the client was interrupted.
  void setUserProfileProperty(UserID userId, String keyName, String valueName);

  /// Remove a specific field from a user’s profile.
  ///
  /// @param userId the [UserID] that'll have a key-value pair removed from its profile.
  /// @param keyName the key name.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixInterruptedException when the client was interrupted.
  void deleteUserProfileProperty(UserID userId, String keyName);
}
