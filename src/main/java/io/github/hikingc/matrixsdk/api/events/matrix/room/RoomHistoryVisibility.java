package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NullMarked;

/// Content information about control of whether a user can see the events that happened in a room
/// from before they joined.
///
/// @param historyVisibility who can see the room history.
@NullMarked
public record RoomHistoryVisibility(
    @JsonProperty(required = true) HistoryVisibilityType historyVisibility)
    implements StateEventContent {}
