package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

public record KeyVerificationKey(
    @NonNull @JsonProperty(required = true) String key,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    String transactionId)
    implements MessageEventContent {}
