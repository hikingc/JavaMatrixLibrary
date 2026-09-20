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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;

/// Holds the ID of an event and its timestamp in milliseconds since the Unix epoch.
///
/// @param eventId The ID of the event found.
/// @param originServerTs The event’s timestamp, in milliseconds since the Unix epoch.
///
///   This makes it easy to do a quick comparison to see if the `event_id` fetched is too far out of
///   range to be useful for your use case.
public record EventTimestamp(
    @JsonProperty(required = true) EventID eventId,
    @JsonProperty(required = true) long originServerTs) {}
