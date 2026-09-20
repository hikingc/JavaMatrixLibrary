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
package io.github.hikingc.matrixsdk.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import org.jspecify.annotations.NonNull;

/// Holds `/whoami` information from the server.
///
/// @param deviceId the Device ID associated with the access token.
///
///   If no device is associated with the access token (such as in the case of application services)
///   then this field can be omitted. Otherwise, this is required.
/// @param isGuest when `true`, the user is a Guest User. When not present or `false`, the user is
///   presumed to be a non-guest user.
/// @param userId the [UserID] that owns the access token.
public record WhoAmI(
    String deviceId, Boolean isGuest, @NonNull @JsonProperty(required = true) UserID userId) {}
