package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomMember;

@JsonTypeName("m.room.member")
public record RoomMemberEvent(
    RoomMember content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    UnsignedData unsigned)
    implements StateEvent<RoomMember> {

  @Override
  public String type() {
    return "m.room.member";
  }
}
