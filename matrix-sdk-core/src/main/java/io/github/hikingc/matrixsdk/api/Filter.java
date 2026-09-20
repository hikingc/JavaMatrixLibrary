/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.filters.FilterDefinition;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.exceptions.MatrixApiException;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;

/// Core interface for executing protocol operations for filtering.
///
/// All operations in this interface are blocking. Implementations must ensure thread safety and
/// avoid synchronization blocks that cause carrier thread pinning during network I/O.
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
  /// @throws MatrixException    if an error occurred while processing.
  /// @throws MatrixApiException if the server returns an unsuccessful answer.
  String publishFilter(UserID userId, FilterDefinition filter);

  /// Retrieve a [FilterDefinition] from the homeserver.
  ///
  /// @param userId   the [UserID] to download a filter for.
  /// @param filterId the filter ID to download.
  /// @return a [FilterDefinition] with all uploaded data.
  /// @throws MatrixException    if an error occurred while processing.
  /// @throws MatrixApiException if the server returns an unsuccessful answer.
  FilterDefinition getFilter(UserID userId, String filterId);
}
