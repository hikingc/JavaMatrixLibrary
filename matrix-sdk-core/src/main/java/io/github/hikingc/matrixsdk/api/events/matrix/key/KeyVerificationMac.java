package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.Map;

/// Content information about when sending the MAC of a device’s key to the partner device. The MAC
/// is calculated using the method given in `message_authentication_code` property of the
/// [`m.key.verification.accept`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationAcceptEvent]
/// message.
///
/// @param keys the MAC of the comma-separated, sorted, list of key IDs given in the mac property,
///   encoded as unpadded base64.
/// @param mRelatesTo required when sent as an in-room message. Indicates the
///   [`m.key.verification.request`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent]
///   that this message is related to. **Note that for encrypted messages, this property should be
///   in the unencrypted portion of the event.**
/// @param mac a [Map] of the key ID to the MAC of the key, using the algorithm in the verification
///   process. The MAC is encoded as unpadded base64.
/// @param transactionId required when sent as a to-device message. An opaque identifier for the
///   verification process. Must be the same as the one used for the
///   [`m.key.verification.start`][io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationStartEvent]
///   message.
public record KeyVerificationMac(
    String keys,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    Map<String, String> mac,
    String transactionId)
    implements MessageEventContent {}
