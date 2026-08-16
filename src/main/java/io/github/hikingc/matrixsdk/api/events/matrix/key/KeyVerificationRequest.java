package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import org.jspecify.annotations.NonNull;

@JsonTypeName("m.key.verification.request")
public record KeyVerificationRequest(
    @NonNull @JsonProperty(required = true) String fromDevice,
    @NonNull @JsonProperty(required = true) List<String> methods,
    Long timestamp,
    String transactionId) implements MessageEventContent {}
