package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import org.jspecify.annotations.NonNull;

public record EphemeralFullyRead(@NonNull @JsonProperty(required = true) String eventId) implements EphemeralContent {}
