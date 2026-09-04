package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.services.utils.handlers.CiphertextDeserializer;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonDeserialize;

/// Content information that represents an encrypted event. It can be used either within a room (in
/// which case it will have all the normal properties in
/// [Room][io.github.hikingc.matrixsdk.api.events.ClientEvent] events), or as a
/// [to-device](https://spec.matrix.org/v1.19/client-server-api/#send-to-device-messaging) event.
///
/// @param algorithm the encryption algorithm used to encrypt this event. The value of this field
///   determines which other properties will be present.
/// @param ciphertext the encrypted content of the event. Either the encrypted payload itself, in
///   the case of a Megolm event, or a map from the recipient Curve25519 identity key to ciphertext
///   information, in the case of an Olm event. For more details, see [Messaging
///   Algorithms](https://spec.matrix.org/v1.19/client-server-api/#messaging-algorithms).
/// @param sessionId the ID of the session used to encrypt the message. Required with `Megolm`.
@NullMarked
public record RoomEncrypted(
    @JsonProperty(required = true) String algorithm,
    @JsonDeserialize(using = CiphertextDeserializer.class) @JsonProperty(required = true)
        Ciphertext ciphertext,
    @Nullable String sessionId)
    implements MessageEventContent {}
