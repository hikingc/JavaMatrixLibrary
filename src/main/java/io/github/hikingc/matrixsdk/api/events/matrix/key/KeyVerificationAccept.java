package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Content information about accepting a previously sent
/// [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
/// message.
///
/// @param commitment the hash (encoded as unpadded base64) of the concatenation of the device’s
///   ephemeral public key (encoded as unpadded base64) and the canonical JSON representation of the
///   content object of the m.key.verification.start message.
/// @param hash the hash method the device is choosing to use, out of the options in the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
/// @param keyAgreementProtocol the key agreement protocol the device is choosing to use, out of the
///   options in the m.key.verification.start message.
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param messageAuthenticationCode the message authentication code method the device is choosing
///   to use, out of the options in the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
/// @param shortAuthenticationString the SAS methods both devices involved in the verification
///   process understand. Must be a subset of the options in the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
/// @param transactionId required when sent as a to-device message. An opaque identifier for the
///   verification process. Must be the same as the one used for the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
@NullMarked
public record KeyVerificationAccept(
    @JsonProperty(required = true) String commitment,
    @JsonProperty(required = true) String hash,
    @JsonProperty(required = true) String keyAgreementProtocol,
    @Nullable @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @JsonProperty(required = true) String messageAuthenticationCode,
    @JsonProperty(required = true) List<String> shortAuthenticationString,
    String transactionId)
    implements MessageEventContent {}
