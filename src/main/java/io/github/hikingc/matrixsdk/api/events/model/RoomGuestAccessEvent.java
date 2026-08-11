package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomGuestAccess;

@JsonTypeName("m.room.guest_access")
public record RoomGuestAccessEvent(
    RoomGuestAccess content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomGuestAccess> {

  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.guest_access";
  }
}
