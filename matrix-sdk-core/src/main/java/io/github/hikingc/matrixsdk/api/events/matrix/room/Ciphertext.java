package io.github.hikingc.matrixsdk.api.events.matrix.room;

import java.util.Map;
import org.jspecify.annotations.NullMarked;

/// Models the union type of the `ciphertext` field in [RoomEncrypted].
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#messaging-algorithms">Messaging
///   algorithms in the spec.</a>
@NullMarked
public sealed interface Ciphertext permits Ciphertext.Megolm, Ciphertext.Olm {
  /// A ciphertext of type Megolm
  ///
  /// @param ciphertext The encrypted payload.
  record Megolm(String ciphertext) implements Ciphertext {}

  /// A ciphertext of Olm type
  ///
  /// @param ciphertexts a map from the recipient Curve25519 identity key to ciphertext information,
  ///   in the case of an Olm event.
  record Olm(Map<String, CiphertextInfo> ciphertexts) implements Ciphertext {}
}
