package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;

import java.util.Map;

public record EphemeralTag(Map<String, Tag> tags) implements EphemeralContent {

  public record Tag(Number order) {}
}
