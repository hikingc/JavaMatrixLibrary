package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

public record CallReject(@NonNull @JsonProperty(required = true) String callId,
                         @NonNull @JsonProperty(required = true) String partyId,
                         @NonNull @JsonProperty(required = true) String version) implements MessageEventContent {}
