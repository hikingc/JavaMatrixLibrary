package io.github.hikingc.matrixsdk.api.events.matrix.room;

import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public sealed interface Ciphertext permits Ciphertext.Megolm, Ciphertext.Olm {

    /// Megolm: the encrypted payload itself.
    record Megolm(String ciphertext) implements Ciphertext {}

    /// Olm: a map from recipient Curve25519 identity key to ciphertext info.
    record Olm(Map<String, CiphertextInfo> ciphertexts) implements Ciphertext {}
}
