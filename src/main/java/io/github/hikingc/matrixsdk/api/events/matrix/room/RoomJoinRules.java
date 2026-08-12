package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

public record RoomJoinRules(AllowCondition allow, String joinRule) implements StateEventContent {

  public record AllowCondition(String roomId, String type) {}
}
