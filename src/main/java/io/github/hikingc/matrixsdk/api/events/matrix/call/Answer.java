package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

public record Answer(
    @NonNull @JsonProperty(required = true) String sdp,
    @NonNull @JsonProperty(required = true) String type) {}
