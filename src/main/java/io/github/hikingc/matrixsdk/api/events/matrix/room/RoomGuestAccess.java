package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

public record RoomGuestAccess(String guestAccess) implements StateEventContent {}
