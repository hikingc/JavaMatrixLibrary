package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.Map;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Event content sent by the caller when they wish to establish a call.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
/// @param invitee the ID of the user being called. If omitted, any user in the room can answer.
/// @param lifetime the time in milliseconds that the invite is valid for. Once the invite age
///   exceeds this value, clients should discard it. They should also no longer show the call as
///   awaiting an answer in the UI.
/// @param offer the session description [Offer]
/// @param sdpStreamMetadata metadata describing the streams that will be sent.
///
///   This is a map of stream ID to metadata about the stream.
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#streams">Streams in the spec.</a>
@NullMarked
public record CallInvite(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @Nullable UserID invitee,
    @JsonProperty(required = true) Integer lifetime,
    @JsonProperty(required = true) Offer offer,
    @Nullable Map<String, StreamMetadata> sdpStreamMetadata)
    implements MessageEventContent, CallEvent {

  /// A session descriptor for [CallInvite#offer()]
  ///
  /// @param sdp the SDP text of the session description.
  public record Offer(@JsonProperty(required = true) String sdp) {
    /// The type of session descriptor
    ///
    /// @return always [CallSessionDescriptorType#ANSWER]
    @JsonProperty("type")
    public CallSessionDescriptorType type() {
      return CallSessionDescriptorType.OFFER;
    }
  }
}
