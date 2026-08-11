package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomAvatar;

@JsonTypeName("m.room.avatar")
public record RoomAvatarEvent(
    RoomAvatar content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomAvatar> {

  @Override
  public String type() {
    return "m.room.avatar";
  }
}
