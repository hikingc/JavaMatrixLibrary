package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import org.jspecify.annotations.NullMarked;

/// Content information about the current location of the user’s read marker in a room. This event appears in the user’s room
/// account data for the room the marker is applicable for.
///
/// @param eventId the event the user’s read marker is located at in the room.
@NullMarked
public record EphemeralFullyRead(@JsonProperty(required = true) String eventId)
    implements EphemeralContent {}
