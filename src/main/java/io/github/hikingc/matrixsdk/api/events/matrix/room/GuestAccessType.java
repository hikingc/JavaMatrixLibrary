package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Represents types of guest access.
public enum GuestAccessType {
  /// Guest can join.
  @JsonProperty("can_join")
  CAN_JOIN,
  /// Guest is forbidden.
  @JsonProperty("forbidden")
  FORBIDDEN
}
