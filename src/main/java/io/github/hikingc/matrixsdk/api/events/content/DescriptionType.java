package io.github.hikingc.matrixsdk.api.events.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DescriptionType {
  @JsonProperty("offer")
  OFFER,
  @JsonProperty("answer")
  ANSWER
}
