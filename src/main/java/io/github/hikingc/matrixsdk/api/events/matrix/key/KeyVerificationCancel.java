package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

public record KeyVerificationCancel(
    @NonNull @JsonProperty(required = true) String code,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @NonNull @JsonProperty(required = true) String reason,
    String transactionId)
    implements MessageEventContent {}


// Code cannot be put into an enum because I am not sure if servers send codes not declared in the spec, I'd rather not lose that information.
// 13/08/2026