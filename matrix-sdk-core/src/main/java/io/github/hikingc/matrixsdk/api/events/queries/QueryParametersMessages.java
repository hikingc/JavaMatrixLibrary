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
package io.github.hikingc.matrixsdk.api.events.queries;

/// Additional arguments required for message retrieval.
///
/// @param from the token to start returning events from. This token can be obtained from a
///   `prev_batch` or `next_batch` token returned from `/sync`ing, or from an end token returned by
///   a previous request to this endpoint.
///
///   This endpoint can also accept a value returned as a start token by a previous request to this
///   endpoint, though servers are not required to support this. Clients should not rely on the
///   behavior.
///
///   If it is not provided, the homeserver shall return a list of messages from the first or last
///   (per the value of the dir parameter) visible event in the room history for the requesting
///   user.
/// @param limit The maximum number of events to return. If not set the default is 10.
/// @param to The token to stop returning events at. This token can be obtained from a `prev_batch`
///   or `next_batch` token returned from `/sync`ing, or from an end token returned by a previous
///   request to this endpoint.
public record QueryParametersMessages(String from, Integer limit, String to) {
  /// Utility instantiation method that notifies the client that no specific queries are required.
  ///
  /// @return An instantiation record with default query parameters.
  public static QueryParametersMessages defaultParams() {
    return new QueryParametersMessages(null, 10, null);
  }
}
