package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.room.*;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceChild;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceParent;

/// Marker interface for input state events.
public sealed interface StateEventContent
        permits RoomAvatar, RoomCanonicalAlias, RoomCreate, RoomGuestAccess, RoomHistoryVisibility, RoomJoinRules, RoomMember, RoomName, RoomPinnedEvents, RoomPowerLevels, RoomTopic, SpaceChild, SpaceParent {}
