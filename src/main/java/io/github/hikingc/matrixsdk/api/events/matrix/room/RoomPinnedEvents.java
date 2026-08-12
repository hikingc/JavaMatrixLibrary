package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

import java.util.List;

public record RoomPinnedEvents(List<String> pinned)
    implements StateEventContent { // Totally not gonna cause confusion
}
