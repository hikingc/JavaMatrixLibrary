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
package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import org.jspecify.annotations.NullMarked;

/// Content information about the current location of the user’s read marker in a room. This event
/// appears in the user’s room
/// account data for the room the marker is applicable for.
///
/// @param eventId the event the user’s read marker is located at in the room.
@NullMarked
public record EphemeralFullyRead(@JsonProperty(required = true) String eventId)
    implements EphemeralContent {}
