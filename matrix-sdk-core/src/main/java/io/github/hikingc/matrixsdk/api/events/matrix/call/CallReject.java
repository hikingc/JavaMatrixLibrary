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
package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NullMarked;

/// Event content to reject a call, If the
/// [`m.call.invite`][io.github.hikingc.matrixsdk.api.events.server.message.CallInviteEvent] event
/// has `version` "1", a client wishing to reject the call sends an
/// [`m.call.reject`][io.github.hikingc.matrixsdk.api.events.server.message.CallRejectEvent] event.
/// This rejects the call on all devices, but if the calling device sees an `answer` before the
/// `reject`, it disregards the reject event and carries on. The reject has a `party_id` just like
/// an answer, and the caller sends a `select_answer` for it just like an answer. If another client
/// had already sent an answer and sees the caller select the reject response instead of its answer,
/// it ends the call.
///
/// If the [`m.call.invite
/// event`][io.github.hikingc.matrixsdk.api.events.server.message.CallInviteEvent] has `version` 0,
/// the callee sends an
/// [`m.call.hangup`][io.github.hikingc.matrixsdk.api.events.server.message.CallHangupEvent] event.
/// If the calling user chooses to end the call before setup is complete, the client sends
/// [`m.call.hangup`][io.github.hikingc.matrixsdk.api.events.server.message.CallHangupEvent] as
/// previously.
///
/// Note that, unlike [`m.call.hangup`][CallHangup], this event has no `reason` field: the rejection
/// of a call is always implicitly **because the user chose not to answer it**.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
@NullMarked
public record CallReject(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version)
    implements MessageEventContent, CallEvent {}
