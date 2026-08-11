package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomName;

@JsonTypeName("m.room.name")
public record RoomNameEvent(
    RoomName content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomName> {

  @Override
  public String type() {
    return "m.room.name";
  }
}
