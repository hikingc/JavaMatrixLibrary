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
import java.net.URI;
import org.jspecify.annotations.NonNull;

/// Content information of when the user’s client presence state change.
///
/// @param avatarUrl the current avatar URL for this user, if any.
/// @param currentlyActive whether the user is currently active
/// @param displayname the current display name for this user, if any.
/// @param lastActiveAgo the last time since this used performed some action, in milliseconds.
/// @param presence the presence state for this user.
/// @param statusMsg an optional description to accompany the presence.
public record EphemeralPresence(
    URI avatarUrl,
    Boolean currentlyActive,
    String displayname,
    Number lastActiveAgo,
    @NonNull @JsonProperty(required = true) PresenceType presence,
    String statusMsg)
    implements EphemeralContent {}
