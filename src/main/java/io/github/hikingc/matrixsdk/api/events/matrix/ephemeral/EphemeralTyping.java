package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.List;

public record EphemeralTyping(List<UserID> userIds) implements EphemeralContent {}
