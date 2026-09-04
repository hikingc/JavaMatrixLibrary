package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomEncryption;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

@JsonTypeName("m.room.encryption")
public record RoomEncryptionEvent(
    RoomEncryption content,
    EventID eventId,
    Long originServerTs,
    RoomID roomId,
    UserID sender,
    String type,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomEncryption> {}
