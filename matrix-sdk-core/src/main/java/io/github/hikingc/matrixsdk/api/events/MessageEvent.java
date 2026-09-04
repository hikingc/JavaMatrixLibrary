package io.github.hikingc.matrixsdk.api.events;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.events.server.message.*;

/// Interface for events which describe transient “once-off” activity in a room: typically
/// communication such as sending an instant message or setting up a VoIP call.
public sealed interface MessageEvent<C extends MessageEventContent> extends ClientEvent<C>
    permits CallAnswerEvent,
        CallCandidatesEvent,
        CallHangupEvent,
        CallInviteEvent,
        CallNegotiateEvent,
        CallRejectEvent,
        CallSelectAnswerEvent,
        KeyVerificationAcceptEvent,
        KeyVerificationCancelEvent,
        KeyVerificationDoneEvent,
        KeyVerificationKeyEvent,
        KeyVerificationMacEvent,
        KeyVerificationRequestEvent,
        KeyVerificationStartEvent,
        ReactionEvent,
        RoomEncryptedEvent,
        RoomMessageEvent,
        RoomRedactionEvent,
        StickerEvent {}
