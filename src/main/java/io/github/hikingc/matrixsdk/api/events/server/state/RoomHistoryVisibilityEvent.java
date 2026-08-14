package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomHistoryVisibility;

@JsonTypeName("m.room.history_visibility")
public record RoomHistoryVisibilityEvent(
    RoomHistoryVisibility content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomHistoryVisibility> {}
