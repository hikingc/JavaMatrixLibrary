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
package io.github.hikingc.matrixsdk.api.events.matrix.space;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Content information where the relationship of a room to a parent space-room is defined.
///
/// @param canonical flag to denote this parent is the primary parent for the room.
///
///   When multiple `canonical` parents are found, the lowest parent when ordering by room ID
///   lexicographically by Unicode code-points should be used. It is `false` if ommited.
/// @param via a [List] of servers to try and join through.
///
///   When not present or invalid, the room is not considered to be part of the parent space.
/// @see <a href="https://spec.matrix.org/v1.19/appendices/#routing">Routing in the spec.</a>
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#spaces">Spaces in spec.</a>
public record SpaceParent(
    Boolean canonical, @NonNull @JsonProperty(required = true) List<String> via)
    implements StateEventContent {}
