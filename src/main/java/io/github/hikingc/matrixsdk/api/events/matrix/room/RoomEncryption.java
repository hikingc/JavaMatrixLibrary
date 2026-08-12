package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NonNull;

public record RoomEncryption(
    @NonNull @JsonProperty(required = true) String algorithm,
    Integer rotationPeriodMs,
    Integer rotationPeriodMsgs)
    implements StateEventContent {}
