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
package io.github.hikingc.matrixsdk.api.userdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import org.jspecify.annotations.NonNull;

/// User information requested from a directory search request.
///
/// Only data field guaranteed to be available is the user's id.
///
/// @param avatarUrl an avatar [URI] prefixed with
///   [mxc://](https://spec.matrix.org/v1.18/client-server-api/#matrix-content-mxc-uris).
/// @param displayName their display name.
/// @param userId their matrix User ID.
public record User(
    URI avatarUrl, String displayName, @NonNull @JsonProperty(required = true) String userId) {}
