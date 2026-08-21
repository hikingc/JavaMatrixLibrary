package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Represents types of presence states for a user.
public enum PresenceType {
  /// User is online.
  @JsonProperty("online")
  ONLINE,
  /// User is offline.
  @JsonProperty("offline")
  OFFLINE,
  /// User is unavailable.
  @JsonProperty("unavailable")
  UNAVAILABLE
}
