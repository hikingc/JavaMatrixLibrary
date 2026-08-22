package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

/// Content information about when a key verification process has begun. Typically sent as a
/// to-device event. The method field determines the type of verification. The fields in the event
/// will differ depending on the method. This definition includes fields that are in common among
/// all variants.
///
/// @param fromDevice the device ID which is initiating the request.
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param method the verification method to use.
/// @param nextMethod optional method to use to verify the other user’s key with. Applicable when
///   the method chosen only verifies one user’s key. This field will never be present if the method
///   verifies keys both ways.
/// @param transactionId required when sent as a to-device message. An opaque identifier for the
///   verification process. Must be unique with respect to the devices involved. Must be the same as
///   the `transaction_id` given in the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   if this process is originating from a request.

public record KeyVerificationStart(
    @NonNull @JsonProperty(required = true) String fromDevice,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @NonNull @JsonProperty(required = true) String method,
    String nextMethod,
    String transactionId)
    implements MessageEventContent {}
