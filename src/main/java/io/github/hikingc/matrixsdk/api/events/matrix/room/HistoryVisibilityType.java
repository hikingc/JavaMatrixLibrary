package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HistoryVisibilityType {
  @JsonProperty("invited")
  INVITED,
  @JsonProperty("joined")
  JOINED,
  @JsonProperty("shared")
  SHARED,
  @JsonProperty("world_readable")
  WORLD_READABLE
}
