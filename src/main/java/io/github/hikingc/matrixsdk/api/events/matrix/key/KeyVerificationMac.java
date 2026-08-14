package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.Map;

public record KeyVerificationMac(
    String keys,
    @JsonProperty("m.relates_to") VerificationRelatesTo mRelatesTo,
    Map<String, String> mac,
    String transactionId)
    implements MessageEventContent {}
