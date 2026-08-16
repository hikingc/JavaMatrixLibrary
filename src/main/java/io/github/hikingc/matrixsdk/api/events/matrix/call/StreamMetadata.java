package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

public record StreamMetadata(
    Boolean audioMuted,
    @NonNull @JsonProperty(required = true) PurposeType purpose,
    Boolean videoMuted) {}
