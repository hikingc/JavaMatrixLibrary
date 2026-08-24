package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Represents types of history visibility.
public enum HistoryVisibilityType {
  /// Events are accessible to newly joined members from the point they were invited onwards. Events
  /// stop being accessible when the member’s state changes to something other than `invite` or
  /// `join`.
  @JsonProperty("invited")
  INVITED,
  /// Events are accessible to newly joined members from the point they joined the room onwards.
  /// Events stop being accessible when the member’s state changes to something other than `join`.
  @JsonProperty("joined")
  JOINED,
  /// Previous events are always accessible to newly joined members. All events in the room are
  /// accessible, even those sent when the member was not a part of the room.
  @JsonProperty("shared")
  SHARED,
  /// All events while this is the value may be shared by any participating homeserver with any
  /// authenticated user, regardless of whether they have ever joined the room. This includes guest
  /// users.
  @JsonProperty("world_readable")
  WORLD_READABLE
}
