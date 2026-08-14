package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;

public record KeyVerificationDone(
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo, String transactionId)
    implements MessageEventContent {}
