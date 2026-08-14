package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceParent;

@JsonTypeName("m.space.parent")
public record SpaceParentEvent(
    SpaceParent content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements StateEvent<SpaceParent> {}
