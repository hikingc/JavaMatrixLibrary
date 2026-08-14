package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.call.*;
import io.github.hikingc.matrixsdk.api.events.matrix.key.*;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomRedaction;
import io.github.hikingc.matrixsdk.api.events.server.message.KeyVerificationRequestEvent;

/// Marker interface for input message events.
public sealed interface MessageEventContent
        permits Reaction, Sticker, CallAnswer, CallCandidates, CallHangup, CallInvite, CallNegotiate, CallReject, CallSelectAnswer, KeyVerificationAccept, KeyVerificationCancel, KeyVerificationDone, KeyVerificationKey, KeyVerificationMac, KeyVerificationRequest, KeyVerificationStart, RoomMessage, RoomRedaction, KeyVerificationRequestEvent {}
