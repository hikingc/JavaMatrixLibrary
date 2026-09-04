package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceChild;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

@JsonTypeName("m.space.child")
public record SpaceChildEvent(
    SpaceChild content,
    EventID eventId,
    Long originServerTs,
    RoomID roomId,
    UserID sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements StateEvent<SpaceChild> {}
