package io.github.hikingc.matrixsdk.api.well_known;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/// Public key information for a [Policy Server](https://spec.matrix.org/v1.19/client-server-api/#policy-servers).
///
/// @param publicKeys  the unpadded base64-encoded public keys for the Policy Server. **MUST contain at least `ed25519`.**
/// @param extraFields the unpadded base64-encoded public key for the key algorithm.
public record PolicyServerInformation(
        @JsonProperty(value = "public_keys", required = true) Map<String, String> publicKeys,
        Map<String, Object> extraFields) {
    /// Deserialization helper to accommodate additional public key fields.
    ///
    /// @param publicKeys extra fields.
    @JsonCreator
    private PolicyServerInformation(
            @JsonProperty(value = "public_keys", required = true) Map<String, String> publicKeys) {
        this(Map.copyOf(publicKeys), Map.of());
    }
}
