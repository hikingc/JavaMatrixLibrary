package io.github.hikingc.matrixsdk.api.events.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Reaction(@JsonProperty("m.relates_to") String mRelatesTo)
    implements MessageEventContent {}
