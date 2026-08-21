package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Represents types of session descriptors.
public enum CallSessionDescriptorType {
  /// Answer type
  @JsonProperty("answer")
  ANSWER,
  /// Offer type
  @JsonProperty("offer")
  OFFER

}
