package io.github.hikingc.matrixsdk.api.events.matrix.key;

import io.github.hikingc.matrixsdk.api.identifiers.EventID;

public record VerificationRelatesTo(EventID eventId, String relType) {}
