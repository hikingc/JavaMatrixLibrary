package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.events.matrix.StreamMetadata;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.Map;
import org.jspecify.annotations.NonNull;

public record CallInvite(
    @NonNull @JsonProperty(required = true) String callId,
    UserID invitee,
    @NonNull @JsonProperty(required = true) Integer lifetime,
    @NonNull @JsonProperty(required = true) Offer offer,
    String partyId,
    Map<String, StreamMetadata> sdpStreamMetadata,
    String version)
    implements MessageEventContent {

  public record Offer(
      @NonNull @JsonProperty(required = true) String sdp,
      @NonNull @JsonProperty(required = true) String type) {}
}
