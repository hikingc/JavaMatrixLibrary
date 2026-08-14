package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

public record KeyVerificationStart(
    @NonNull @JsonProperty(required = true) String fromDevice,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @NonNull @JsonProperty(required = true) String method,
    String nextMethod,
    String transactionId)
    implements MessageEventContent {

}
