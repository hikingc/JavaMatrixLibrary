package io.github.hikingc.matrixsdk.api.events.content;

/// Marker interface for input message events.
public sealed interface MessageEventContent
        permits CallAnswer, CallCandidates, CallInvite, CallNegotiate, CallSelectAnswer, Reaction, RoomMessage, RoomRedaction, Sticker {}
