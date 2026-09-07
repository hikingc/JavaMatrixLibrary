package io.github.hikingc.matrixsdk.api.well_known;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;

public record PolicyServerInformation(String publicKeys, Map<String, Object> otherProperties) {
  /// Deserialization helper to accommodate additional unknown fields.
  ///
  /// @param raw input key-values from a response.
  /// @return deserialized [PolicyServerInformation] with corresponding values.
  @JsonCreator
  public static PolicyServerInformation of(Map<String, Object> raw) {
    Map<String, Object> copy = new HashMap<>(raw);
    String room = (String) copy.remove("public_keys");
    return new PolicyServerInformation(room, Map.copyOf(copy));
  }
}
