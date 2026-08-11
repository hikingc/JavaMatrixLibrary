package io.github.hikingc.matrixsdk.api.events.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

public record CallSelectAnswer(
    @NonNull @JsonProperty(required = true) String callId,
    @NonNull @JsonProperty(required = true) String partyId,
    @NonNull @JsonProperty(required = true) String selectedPartyId,
    @NonNull @JsonProperty(required = true) String version)
    implements MessageEventContent {}
