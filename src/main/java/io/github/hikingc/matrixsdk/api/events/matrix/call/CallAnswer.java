package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.Map;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Event content to be sent by the callee when they wish to answer the call.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
/// @param version the version of the VoIP specification this message adheres to. This specification
/// @param answer the session descriptor. device ID from end-to-end cryptography for the value of
///   this field.
/// @param sdp_stream_metadata metadata describing the streams that will be sent.
///
///   This is a map of stream ID to metadata about the stream. is version 1.
@NullMarked
public record CallAnswer(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @JsonProperty(required = true) Answer answer,
    @Nullable Map<String, StreamMetadata> sdp_stream_metadata)
    implements MessageEventContent, CallEvent {
  /// A session descriptor for [CallAnswer#answer()]
  ///
  /// @param sdp the SDP text of the session description.
  @NullMarked
  public record Answer(@JsonProperty(required = true) String sdp) {

    /// The type of session descriptor
    ///
    /// @return always [CallSessionDescriptorType#ANSWER]
    @JsonProperty("type")
    public CallSessionDescriptorType type() {
      return CallSessionDescriptorType.ANSWER;
    }
  }
}
