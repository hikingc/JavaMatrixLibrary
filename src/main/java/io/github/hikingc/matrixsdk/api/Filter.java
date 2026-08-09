package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.filters.FilterDefinition;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import io.github.hikingc.matrixsdk.exceptions.MatrixNetworkException;

/// Core interface for executing protocol operations for filtering.
///
/// All operations in this interface are blocking. Implementations must ensure thread safety and
/// avoid synchronization blocks that cause carrier thread pinning during network I/O.
///
/// Unless otherwise noted, every method in this interface throws [MatrixIOException] if the request
/// or response payload cannot be processed, and [MatrixNetworkException] if the server's response
/// status is not successful.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#filtering>Matrix Client-Server
///   API Specification for Filters</a>
public interface Filter {

  /// Uploads a new filter definition to the homeserver. Returns a filter ID that may be used in
  /// future requests to restrict which events are returned to the client.
  ///
  /// @param userId the [UserID] of whoever is uploading the server.
  /// @param filter the definition of the filter.
  /// @return an ID of the filter definition, usable in supported endpoints.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixNetworkException when the response status is not successful.
  String publishFilter(UserID userId, FilterDefinition filter);

  /// Retrieve a [FilterDefinition] from the homeserver.
  ///
  /// @param userId the [UserID] to download a filter for.
  /// @param filterId the filter ID to download.
  /// @return a [FilterDefinition] with all uploaded data.
  /// @throws MatrixIOException when the payload cannot be processed.
  /// @throws MatrixNetworkException when the response status is not successful.
  FilterDefinition getFilter(UserID userId, String filterId);
}
