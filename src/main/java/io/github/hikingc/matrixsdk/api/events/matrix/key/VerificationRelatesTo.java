package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;

/// Represents an event relationship with another one. This object is specifically modeled for the
/// `call` namespace.
///
/// @param eventId an ID of an event that is related to the main event.
public record VerificationRelatesTo(EventID eventId) {

  /// The type of relationship. Currently only `m.reference`
  ///
  /// @return always `m.reference`
  @JsonProperty("rel_type")
  public String getRelType() {
    return "m.reference";
  }
}
