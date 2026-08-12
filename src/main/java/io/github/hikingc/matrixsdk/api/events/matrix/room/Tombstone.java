package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NonNull;

public record Tombstone(
    @NonNull @JsonProperty(required = true) String body,
    @NonNull @JsonProperty(required = true) String replacementRoom)
    implements StateEventContent {}
