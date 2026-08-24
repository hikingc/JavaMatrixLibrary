package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;

/// Content information about an event which has been redacted.
///
/// @param reason of the redaction, if any.
/// @param redacts the [EventID] that was redacted. Required for, and present starting in, room version 11.
public record RoomRedaction(String reason, EventID redacts) implements MessageEventContent {}
