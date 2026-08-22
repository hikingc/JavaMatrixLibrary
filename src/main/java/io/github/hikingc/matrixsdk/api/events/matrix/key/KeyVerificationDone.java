package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;

/// Content information about an indication that a verification process/request has completed
/// successfully.
///
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param transactionId required when sent as a to-device message. The opaque identifier for the
///   verification process/request.
public record KeyVerificationDone(
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo, String transactionId)
    implements MessageEventContent {}
