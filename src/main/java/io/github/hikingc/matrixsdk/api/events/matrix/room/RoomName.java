package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NullMarked;

/// Content information about the human-friendly name of a room (not a
/// [RoomID][io.github.hikingc.matrixsdk.api.identifiers.RoomID] or a
/// [RoomAlias][io.github.hikingc.matrixsdk.api.identifiers.RoomAlias]).
///
/// @param name the name of the room.
@NullMarked
public record RoomName(@JsonProperty(required = true) String name) implements StateEventContent {}

// the spec is a bit confusing, it says that "If a room has an m.room.name event with an absent,
// null, or empty name field, it should be treated the same as a room with no m.room.name event."
// yet it marks it as a required field? What servers do this man?