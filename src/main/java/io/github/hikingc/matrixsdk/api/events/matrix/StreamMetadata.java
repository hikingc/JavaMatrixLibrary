package io.github.hikingc.matrixsdk.api.events.matrix;

import com.fasterxml.jackson.annotation.JsonGetter;

public record StreamMetadata(Boolean audioMuted, Boolean videoMuted) {
  @JsonGetter("type")
  public String getType() {
    return "answer";
  }
}
