package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomPinnedEvents;

@JsonTypeName("m.room.pinned_events")
public record RoomPinnedEventsEvent(
    RoomPinnedEvents content, // Yeah, I know...
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomPinnedEvents> {

  @Override
  public String type() {
    return "m.room.pinned_events";
  }
}
