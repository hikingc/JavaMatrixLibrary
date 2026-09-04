package io.github.hikingc.matrixsdk.api.events.server.ephemeral;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralTyping;

@JsonTypeName("m.typing")
public record TypingEvent(EphemeralTyping content, String type)
    implements EphemeralEvent<EphemeralTyping> {}
