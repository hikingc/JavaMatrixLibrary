package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomCanonicalAlias;

@JsonTypeName("m.room.canonical.alias")
public record RoomCanonicalAliasEvent(
    RoomCanonicalAlias content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomCanonicalAlias> {}
