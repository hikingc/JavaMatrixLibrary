package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomTombstone;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

@JsonTypeName("m.room.tombstone")
public record RoomTombstoneEvent(
    RoomTombstone content,
    EventID eventId,
    Long originServerTs,
    RoomID roomId,
    UserID sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements StateEvent<RoomTombstone> {}
