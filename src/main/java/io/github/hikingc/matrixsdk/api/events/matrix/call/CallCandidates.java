package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

public record CallCandidates(
    @NonNull @JsonProperty(required = true) String callId,
    @NonNull @JsonProperty(required = true) Candidate candidates,
    @NonNull @JsonProperty(required = true) String partyId,
    @NonNull @JsonProperty(required = true) String version)
    implements MessageEventContent {

  public record Candidate(
      @NonNull @JsonProperty(required = true) String candidate,
      Number sdpMLineIndex,
      String sdpMid) {}
}
