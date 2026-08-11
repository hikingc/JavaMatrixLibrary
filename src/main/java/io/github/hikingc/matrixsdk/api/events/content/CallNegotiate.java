package io.github.hikingc.matrixsdk.api.events.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.jspecify.annotations.NonNull;

public record CallNegotiate(
    @NonNull @JsonProperty(required = true) String callId,
    @NonNull @JsonProperty(required = true) Description description,
    @NonNull @JsonProperty(required = true) Integer lifetime,
    @NonNull @JsonProperty(required = true) String partyId,
    Map<String, StreamMetadata> sdpStreamMetadata,
    @NonNull @JsonProperty(required = true) String version)
    implements MessageEventContent {

  public record Description(
      @NonNull @JsonProperty(required = true) String sdp,
      @NonNull @JsonProperty(required = true) DescriptionType type) {}
}
