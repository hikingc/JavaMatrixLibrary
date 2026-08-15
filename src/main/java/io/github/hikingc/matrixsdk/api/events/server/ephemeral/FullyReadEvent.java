package io.github.hikingc.matrixsdk.api.events.server.ephemeral;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralFullyRead;

@JsonTypeName("m.fully_read")
public record FullyReadEvent(EphemeralFullyRead content, String type)
    implements EphemeralEvent<EphemeralFullyRead> {}
