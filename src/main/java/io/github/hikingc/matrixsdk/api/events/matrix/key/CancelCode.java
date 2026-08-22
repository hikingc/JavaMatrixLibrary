package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public sealed interface CancelCode {

  enum Known implements CancelCode {
    USER("m.user"),
    TIMEOUT("m.timeout"),
    UNKNOWN_TRANSACTION("m.unknown_transaction"),
    UNKNOWN_METHOD("m.unknown_method"),
    UNEXPECTED_MESSAGE("m.unexpected_message"),
    KEY_MISMATCH("m.key_mismatch"),
    USER_MISMATCH("m.user_mismatch"),
    INVALID_MESSAGE("m.invalid_message"),
    ACCEPTED("m.accepted"),
    MISMATCHED_COMMITMENT("m.mismatched_commitment"),
    MISMATCHED_SAS("m.mismatched_sas");

    private final String wireValue;

    Known(String wireValue) {
      this.wireValue = wireValue;
    }

    @Override
    public String asString() {
      return wireValue;
    }
  }

  record Custom(String value) implements CancelCode {
    @Override
    public String asString() {
      return value;
    }
  }

  String asString();

  @JsonValue
  default String toJson() {
    return asString();
  }

  @JsonCreator
  static CancelCode fromJson(String value) {
    for (Known k : Known.values()) {
      if (k.wireValue.equals(value)) {
        return k;
      }
    }
    return new Custom(value);
  }
}
