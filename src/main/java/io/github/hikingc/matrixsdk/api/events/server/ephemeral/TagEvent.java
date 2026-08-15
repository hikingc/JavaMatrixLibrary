package io.github.hikingc.matrixsdk.api.events.server.ephemeral;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralTag;

@JsonTypeName("m.tag")
public record TagEvent(EphemeralTag content, String type) implements EphemeralEvent<EphemeralTag> {}
