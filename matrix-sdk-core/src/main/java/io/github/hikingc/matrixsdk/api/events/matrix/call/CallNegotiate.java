package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.Map;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Event content that provides SDP negotiation semantics for media pause, hold/resume, ICE restarts
/// and voice/video call up/downgrading. Clients should implement and honor hold functionality as
/// per WebRTC’s recommendation.
///
/// If both the invite event and the accepted answer event have `version` equal to "1", either party
/// may send [`m.call.negotiate`][CallNegotiate] with a description field to offer new SDP to the
/// other party. This event has `call_id` with the ID of the call and `party_id` equal to the
/// client’s party ID for that call. The caller ignores any negotiate events with `party_id` +
/// `user_id` tuple not equal to that of the answer it accepted and the callee ignores any negotiate
/// events with `party_id` + `user_id` tuple not equal to that of the caller. Clients should use the
/// `party_id` field to ignore the remote echo of their own negotiate events.
///
/// This has a `lifetime` field as in [`m.call.invite`][CallInvite], after which the sender of the
/// negotiate event should consider the negotiation failed (timed out) and the recipient should
/// ignore it.
///
/// The `description` field is the same as the offer field in [`m.call.invite`][CallInvite] and
/// `answer` field in [`m.call.answer`][CallAnswer] and is an `RTCSessionDescriptionInit` object as
/// per [The WebRTC specification](https://www.w3.org/TR/webrtc/#dom-rtcsessiondescriptioninit).
///
/// Once an
/// [`m.call.negotiate`][io.github.hikingc.matrixsdk.api.events.server.message.CallNegotiateEvent]
/// is received, the client must respond with another
/// [`m.call.negotiate`][io.github.hikingc.matrixsdk.api.events.server.message.CallNegotiateEvent] ,
/// with the SDP answer (with `"type": "answer"`) in the description property.
///
/// In the [`m.call.invite`][io.github.hikingc.matrixsdk.api.events.server.message.CallInviteEvent]
/// and [`m.call.answer`][io.github.hikingc.matrixsdk.api.events.server.message.CallAnswerEvent]
/// events, the `offer` and `answer` fields respectively are objects of type
/// `RTCSessionDescriptionInit`. Hence, the `type` field, whilst redundant in these events, is
/// included for ease of working with the WebRTC API and is mandatory. Receiving clients should not
/// attempt to validate the `type` field, but simply pass the object into the WebRTC API.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
/// @param description
/// @param lifetime
/// @param sdpStreamMetadata
@NullMarked
public record CallNegotiate(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @JsonProperty(required = true) Description description,
    @JsonProperty(required = true) Integer lifetime,
    @Nullable Map<String, StreamMetadata> sdpStreamMetadata)
    implements MessageEventContent, CallEvent {
  /// A session descriptor for [CallNegotiate#description()()]
  ///
  /// @param sdp the SDP text of the session description.
  /// @param type either [CallSessionDescriptorType#OFFER] or [CallSessionDescriptorType#ANSWER]
  public record Description(
      @JsonProperty(required = true) String sdp,
      @JsonProperty(required = true) CallSessionDescriptorType type) {}
}
