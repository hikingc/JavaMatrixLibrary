package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomJoinRules;

@JsonTypeName("m.room.join_rules")
public record RoomJoinRulesEvent(
    RoomJoinRules content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomJoinRules> {

  @Override
  public String type() {
    return "m.room.join_rules";
  }
}
