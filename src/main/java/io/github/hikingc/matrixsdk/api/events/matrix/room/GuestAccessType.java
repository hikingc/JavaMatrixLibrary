package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GuestAccessType {
  @JsonProperty("can_join")
  CAN_JOIN,
  @JsonProperty("forbidden")
  FORBIDDEN
}
