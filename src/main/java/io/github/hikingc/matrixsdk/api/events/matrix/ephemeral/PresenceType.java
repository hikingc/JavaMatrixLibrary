package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PresenceType {
  @JsonProperty("online")
  ONLINE,
  @JsonProperty("offline")
  OFFLINE,
  @JsonProperty("unavailable")
  UNAVAILABLE
}
