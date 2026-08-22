package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Content information about a cancellation of a key verification process/request.
///
/// @param code the error code for why the process/request was canceled by the user. **Error codes
///   should use the Java package naming convention if not in the following enum
///   [CancelCode.Known]**
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param reason a human-readable description of the `code`. **The client should only rely on this
///   string if it does not understand the `code`.**
/// @param transactionId required when sent as a to-device message. The opaque identifier for the
///   verification process/request.
@NullMarked
public record KeyVerificationCancel(
    @JsonProperty(required = true) CancelCode code,
    @Nullable @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @JsonProperty(required = true) String reason,
    String transactionId)
    implements MessageEventContent {}

// Inspired by `ruma-sdk`, an Interface has been shaped to strengthen the model.
