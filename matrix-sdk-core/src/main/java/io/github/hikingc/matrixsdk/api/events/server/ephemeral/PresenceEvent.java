package io.github.hikingc.matrixsdk.api.events.server.ephemeral;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralPresence;

@JsonTypeName("m.presence")
public record PresenceEvent(EphemeralPresence content, String type)
    implements EphemeralEvent<EphemeralPresence> {}
