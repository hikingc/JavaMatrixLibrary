package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;

public record RoomRedaction(String reason, EventID redacts) implements MessageEventContent {}
