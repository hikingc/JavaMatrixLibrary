package io.github.hikingc.matrixsdk.api.events.server.ephemeral;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralDirect;

@JsonTypeName("m.direct")
public record DirectEvent(EphemeralDirect content, String type)
    implements EphemeralEvent<EphemeralDirect> {}
