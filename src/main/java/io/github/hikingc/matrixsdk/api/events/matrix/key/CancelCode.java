package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/// Models types of cancellation codes, both known by spec and unknown and modeled by servers.
public sealed interface CancelCode {

  /// Types of known cancellation codes.
  enum Known implements CancelCode {
    /// The user canceled the verification.
    USER("m.user"),
    /// The verification process timed out. Verification processes can define their own timeout
    /// parameters.
    TIMEOUT("m.timeout"),
    /// The device does not know about the given transaction ID.
    UNKNOWN_TRANSACTION("m.unknown_transaction"),
    /// The device does not know how to handle the requested method. This should be sent for
    /// `m.key.verification.start` messages and messages defined by individual verification
    /// processes.
    UNKNOWN_METHOD("m.unknown_method"),
    /// The device received an unexpected message. Typically raised when one of the parties is
    /// handling the verification out of order.
    UNEXPECTED_MESSAGE("m.unexpected_message"),
    /// The key was not verified.
    KEY_MISMATCH("m.key_mismatch"),
    /// The expected user did not match the user verified.
    USER_MISMATCH("m.user_mismatch"),
    /// The message received was invalid.
    INVALID_MESSAGE("m.invalid_message"),
    /// A `m.key.verification.request` was accepted by a different device. The device receiving this
    /// error can ignore the verification request.
    ACCEPTED("m.accepted"),
    /// The hash commitment did not match. **Used for Short Authentication String (SAS)
    /// verification**
    MISMATCHED_COMMITMENT("m.mismatched_commitment"),
    /// The SAS did not match. **Used for Short Authentication String (SAS) verification**
    MISMATCHED_SAS("m.mismatched_sas"); // Are these 2 ONLY for SAS?

    private final String wireValue;

    Known(String wireValue) {
      this.wireValue = wireValue;
    }

    @Override
    public String asString() {
      return wireValue;
    }
  }

  /// A type of cancellation code.
  ///
  /// @param value the [String] representation of the code.
  record Custom(String value) implements CancelCode {
    @Override
    public String asString() {
      return value;
    }
  }

  /// String representation of the code.
  ///
  /// @return the code.
  String asString();

  /// Serializes the code into JSON.
  ///
  /// @return the code.
  @JsonValue
  default String toJson() {
    return asString();
  }

  /// Checks if a code is not in the spec defined values and if so, creates one [Custom] value
  ///
  /// @param value the value received
  /// @return a [Custom] code.
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
