package io.github.hikingc.matrixsdk.api.events.matrix.room;

/// Ciphertext Information used by [RoomEncrypted].
///
/// @param body the encrypted payload.,
/// @param type the Olm message type.
public record CiphertextInfo(String body, Integer type) {}
