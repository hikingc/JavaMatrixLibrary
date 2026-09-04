package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.key.KeyVerificationKey;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

@JsonTypeName("m.key.verification.key")
public record KeyVerificationKeyEvent(
    KeyVerificationKey content,
    EventID eventId,
    Long originServerTs,
    RoomID roomId,
    UserID sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<KeyVerificationKey> {}
