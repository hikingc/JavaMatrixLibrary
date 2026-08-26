package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.room.*;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceChild;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceParent;

/// Marker interface for input state content type events.
public sealed interface StateEventContent
    permits RoomAvatar,
        RoomCanonicalAlias,
        RoomCreate,
        RoomEncryption,
        RoomGuestAccess,
        RoomHistoryVisibility,
        RoomJoinRules,
        RoomMember,
        RoomName,
        RoomPinnedEvents,
        RoomPowerLevels,
        RoomTopic,
        RoomServerAcl,
        RoomThirdPartyInvite,
        RoomTombstone,
        SpaceChild,
        SpaceParent {}
