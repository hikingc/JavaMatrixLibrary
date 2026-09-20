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
package io.github.hikingc.matrixsdk.api.events;

import java.util.List;

/// Holds a list of messages and state events from a room.
///
/// @param start a token corresponding to the start of chunk. This will be the same as the value
///   given in `from`.
/// @param end a token corresponding to the end of chunk. This token can be passed back to this
///   endpoint to request further events.
/// @param chunk a list of room events. This list will be paginated according to the [Chronological
///   Direction][io.github.hikingc.matrixsdk.api.events.queries.ChronologicalDirection] used in the
///   request.
/// @param state a list of state events relevant to showing the chunk. For example, if
///   lazy_load_members is enabled in the filter then this may contain the membership events for the
///   senders of events in the chunk.
public record Messages(
    String start, String end, List<ClientEvent<?>> chunk, List<ClientEvent<?>> state) {}
