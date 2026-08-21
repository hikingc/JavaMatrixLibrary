package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NullMarked;

/// Event content that is sent by the caller’s client once it has decided which other client to talk
/// to, by selecting one of multiple possible incoming
/// [`m.call.answer`][io.github.hikingc.matrixsdk.api.events.server.message.CallAnswerEvent] events.
/// Its `selected_party_id` field indicates the answer it’s chosen. The `call_id` and `party_id` of
/// the caller is also included. If the callee’s client sees a `select_answer` for an answer with
/// party ID other than the one it sent, it ends the call and informs the user the call was answered
/// elsewhere. It does not send any events. Media can start flowing before this event is seen or
/// even sent. **Clients that implement previous versions of this specification will ignore this
/// event and behave as they did before.**
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
/// @param selectedPartyId the `party_id` field from the answer event that the caller chose.
@NullMarked
public record CallSelectAnswer(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @JsonProperty(required = true) String selectedPartyId)
    implements MessageEventContent, CallEvent {}
