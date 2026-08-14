package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceChild;

@JsonTypeName("m.space.child")
public record SpaceChildEvent(
    SpaceChild content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements StateEvent<SpaceChild> {}
