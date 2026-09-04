package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NullMarked;

/// Content information about tombstones, a way of signifying that a room has been upgraded to a
/// different room version, and that clients should go there.
///
/// @param body a server-defined message.
/// @param replacementRoom the room ID of the new room the client should be visiting.
@NullMarked
public record RoomTombstone(
    @JsonProperty(required = true) String body,
    @JsonProperty(required = true) String replacementRoom)
    implements StateEventContent {}
