package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomPowerLevels;

@JsonTypeName("m.room.power_levels")
public record RoomPowerLevelsEvent(
    RoomPowerLevels content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomPowerLevels> {

  @Override
  public String type() {
    return "m.room.power_levels";
  }
}
