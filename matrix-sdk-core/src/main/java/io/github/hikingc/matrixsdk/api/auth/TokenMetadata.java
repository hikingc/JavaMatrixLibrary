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

/// Holds access token data as defined in the OAuth 2.0 spec.
///
/// @param accessToken the access token issued by the server.
/// @param tokenType information on the type of token to use.
/// @param expiresIn lifetime in seconds of the access token.
/// @param refreshToken used to obtain new access tokens using the same auth grant.
/// @param scope the scope of the `access token`.
/// @see <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-5.1">the RFC 6749 OAuth
///   framework spec</a>
public record TokenMetadata(
    @JsonProperty(required = true) String accessToken,
    @JsonProperty(required = true) String tokenType,
    Integer expiresIn,
    String refreshToken,
    String scope) {}
