package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// The spec actually has typos on here... - 21/aug/2026
/// Event content sent by either party to signal their termination of the call. This can be sent
/// either once the call has been established or before to abort the call.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
/// @param reason reason for the hangup. Note that this was optional in previous versions of the
///   spec, so a missing value should be treated as user_hangup.
@NullMarked
public record CallHangup(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @Nullable ReasonType reason)
    implements MessageEventContent, CallEvent {

  /// Normalizes null mute-state fields to their default (unmuted) values.
  public CallHangup {
    if (reason == null) {
      reason = ReasonType.USER_HANGUP;
    }
  }
}
