package io.github.hikingc.matrixsdk.api.events;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.events.server.message.*;

/// Interface for events which describe transient “once-off” activity in a room: typically
/// communication such as sending an instant message or setting up a VoIP call.
public sealed interface MessageEvent<C extends MessageEventContent> extends ClientEvent<C>
        permits CallAnswerEvent, CallCandidatesEvent, CallInviteEvent, CallNegotiateEvent, CallRejectEvent, CallSelectAnswerEvent, ReactionEvent, RoomMessageEvent, RoomRedactionEvent, StickerEvent {}
