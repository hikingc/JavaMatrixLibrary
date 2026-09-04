package io.github.hikingc.matrixsdk.api.events.server;

import io.github.hikingc.matrixsdk.api.events.ClientEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

public record UnknownEvent(
    Object content,
    EventID eventId,
    Long originServerTs,
    RoomID roomId,
    UserID sender,
    String type,
    UnsignedData unsigned)
    implements ClientEvent<Object> {}
