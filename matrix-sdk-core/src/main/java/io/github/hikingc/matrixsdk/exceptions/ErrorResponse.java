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
package io.github.hikingc.matrixsdk.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

/// The record used by all endpoints when the operation is not successful.
///
/// @apiNote The field `retry_after_ms` is deprecated in the response body `JSON` in favor of the
///   `Retry-After` header. If the server returns both, the header has to override the body,
/// otherwise it will be
///   `null`.
///
/// @param errCode a specification defined error code.
/// @param error a human-readable error message.
/// @param retryAfterMs a property that MAY be included to tell the client how long they have to
///   wait in milliseconds before they can try again.
public record ErrorResponse(
    @JsonProperty("errcode") String errCode, String error, Long retryAfterMs) {}
// TODO  add additional parameters
// https://spec.matrix.org/v1.19/client-server-api/#common-error-codes
