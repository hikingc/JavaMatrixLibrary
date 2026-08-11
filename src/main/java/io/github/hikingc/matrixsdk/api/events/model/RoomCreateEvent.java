package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomCreate;

@JsonTypeName("m.room.create")
public record RoomCreateEvent(
    RoomCreate content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomCreate> {
  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.create";
  }
}
