package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.call.*;
import io.github.hikingc.matrixsdk.api.events.matrix.key.*;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomEncrypted;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomRedaction;

/// Marker interface for input message content type events.
public sealed interface MessageEventContent
    permits Reaction,
        Sticker,
        CallAnswer,
        CallCandidates,
        CallHangup,
        CallInvite,
        CallNegotiate,
        CallReject,
        CallSelectAnswer,
        KeyVerificationAccept,
        KeyVerificationCancel,
        KeyVerificationDone,
        KeyVerificationKey,
        KeyVerificationMac,
        KeyVerificationRequest,
        KeyVerificationStart,
        RoomEncrypted,
        RoomMessage,
        RoomRedaction {}
