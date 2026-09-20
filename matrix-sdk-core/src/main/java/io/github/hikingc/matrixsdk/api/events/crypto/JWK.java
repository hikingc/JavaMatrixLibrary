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
package io.github.hikingc.matrixsdk.api.events.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/// Represents a JSON Web Key (JWK) values for a symmetric key for cryptographic operations.
///
/// @param kty The key type. Must be `oct`.
/// @param keyOps The key operations. Must at least contain `encrypt` and `decrypt`.
/// @param alg The algorithm. Must be `A256CTR`.
/// @param k The symmetric key, encoded as URL-safe unpadded base64.
/// @param ext Extractable indicator. Must be `true` (W3C extension).
/// @see <a href="https://datatracker.ietf.org/doc/html/rfc7517">RFC7515 specification of JSON Web
// Key (JWK)</a>
public record JWK(
    @JsonProperty(required = true) String kty,
    @JsonProperty(required = true) List<String> keyOps,
    @JsonProperty(required = true) String alg,
    @JsonProperty(required = true) String k,
    boolean ext) {

  /// A JSON Web Key (JWK) representing a symmetric key for cryptographic operations.
  ///
  /// @param kty The key type. Must be `oct`.
  /// @param keyOps The key operations. Must at least contain `encrypt` and `decrypt`.
  /// @param alg The algorithm. Must be `A256CTR`.
  /// @param k The symmetric key, encoded as URL-safe unpadded base64.
  /// @param ext Extractable indicator. Must be `true` (W3C extension).
  public JWK {
    Objects.requireNonNull(kty(), "KTY must be set");
    Objects.requireNonNull(alg(), "ALG must be set");
    Objects.requireNonNull(k(), "K must be set");

    Objects.requireNonNull(keyOps, "keyOps must not be null");
    if (!keyOps.contains("encrypt") || !keyOps.contains("decrypt")) {
      throw new IllegalArgumentException("encrypt and decrypt must be contained");
    }
  }
}
