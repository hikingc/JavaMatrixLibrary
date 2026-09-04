package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

/// Content information about sending the ephemeral public key for a device to the partner device.
///
/// @param key the device’s ephemeral public key, encoded as unpadded base64.
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param transactionId required when sent as a to-device message. An opaque identifier for the
///   verification process. Must be the same as the one used for the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
public record KeyVerificationKey(
    @NonNull @JsonProperty(required = true) String key,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    String transactionId)
    implements MessageEventContent {}
