package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

public record KeyVerificationAccept(
    @NonNull @JsonProperty(required = true) String commitment,
    @NonNull @JsonProperty(required = true) String hash,
    @NonNull @JsonProperty(required = true) String keyAgreementProtocol,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    @NonNull @JsonProperty(required = true) String messageAuthenticationCode,
    @NonNull @JsonProperty(required = true) List<String> shortAuthenticationString,
    String transactionId)
    implements MessageEventContent {}
