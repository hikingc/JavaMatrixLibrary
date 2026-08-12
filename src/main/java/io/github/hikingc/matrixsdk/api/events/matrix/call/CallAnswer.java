package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.api.events.matrix.StreamMetadata;
import org.jspecify.annotations.NonNull;

public record CallAnswer(
    @NonNull @JsonProperty(required = true) Answer answer,
    @NonNull @JsonProperty(required = true) String callId,
    @NonNull @JsonProperty(required = true) String partyId,
    Map<String, StreamMetadata> sdp_stream_metadata,
    @NonNull @JsonProperty(required = true) String version)
    implements MessageEventContent {
}
