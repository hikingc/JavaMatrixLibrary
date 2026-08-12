package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.call.*;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomRedaction;

/// Marker interface for input message events.
public sealed interface MessageEventContent
    permits CallAnswer,
        CallCandidates,
        CallInvite,
        CallNegotiate,
        CallReject,
        CallSelectAnswer,
        Reaction,
        RoomMessage,
        RoomRedaction,
        Sticker {}
