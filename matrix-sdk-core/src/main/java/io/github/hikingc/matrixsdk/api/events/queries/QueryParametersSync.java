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

/// Represents the arguments required to format the [java.net.URI] used to query the `/sync`
/// endpoint.
///
/// @param filter The ID of a filter created using the filter API or a filter JSON object encoded as
///   a [String].
/// @param fullState Controls whether to include the full state for all rooms the user is a member
///   of. The default is `false`.
/// @param setPresence Controls whether this client is automatically marked as online by polling
///   this API. If this parameter is omitted then this client is automatically marked as online when
///   it uses this API. Otherwise, if the parameter is set to `offline` then the client is not
///   marked as being online when it uses this API. When set to `unavailable`, the client is marked
///   as being idle.
/// @param since A point in time to continue a sync from. This should be the `next_batch` token
///   returned by an earlier call to this endpoint.
/// @param timeout The maximum time to wait, in milliseconds, before returning this request. Default
///   is 0
/// @param useStateAfter Controls whether to receive state changes between the **previous sync and
///   the start of the timeline**, or **between the previous sync and the end of the timeline**.
/// @see <a href="https://spec.matrix.org/latest/client-server-api/#filtering">Filter spec details
///   for more information about filtering</a>
public record QueryParametersSync(
    String filter,
    boolean fullState,
    String setPresence,
    String since,
    Integer timeout,
    Boolean useStateAfter) {}
