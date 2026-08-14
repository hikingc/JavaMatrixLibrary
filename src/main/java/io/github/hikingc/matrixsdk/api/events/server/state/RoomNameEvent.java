package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomName;

@JsonTypeName("m.room.name")
public record RoomNameEvent(
    RoomName content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomName> {}
