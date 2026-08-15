package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.List;
import java.util.Map;

public record EphemeralDirect(@JsonValue Map<UserID, List<String>> directs) implements EphemeralContent {}
