package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

public record RoomHistoryVisibility(String history_visibility)
    implements StateEventContent { // One of: [invited, joined, shared, world_readable].
}
