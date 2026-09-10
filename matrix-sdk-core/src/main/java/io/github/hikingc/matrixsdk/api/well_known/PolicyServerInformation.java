package io.github.hikingc.matrixsdk.api.well_known;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record PolicyServerInformation(
        @JsonProperty(value = "public_keys", required = true) Map<String, String> publicKeys,
        Map<String, Object> extraFields) {

    @JsonCreator
    public PolicyServerInformation(
            @JsonProperty(value = "public_keys", required = true) Map<String, String> publicKeys) {
        this(Map.copyOf(publicKeys), Map.of());
    }
}
