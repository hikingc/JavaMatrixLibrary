package io.github.hikingc.matrixsdk.api.events.content;

import io.github.hikingc.matrixsdk.api.identifiers.EventID;

public record RoomRedaction(String reason, EventID redacts) implements MessageEventContent {}
