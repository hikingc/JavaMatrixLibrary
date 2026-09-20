/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
