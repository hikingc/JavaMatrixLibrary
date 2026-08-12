package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;

/// Represents all the types `m.room.message` events from the server.
@JsonTypeName("m.room.message")
public record RoomMessageEvent(
    RoomMessage content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements MessageEvent<RoomMessage> {

  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.message";
  }
}
