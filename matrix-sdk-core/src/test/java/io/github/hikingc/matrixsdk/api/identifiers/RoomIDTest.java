package io.github.hikingc.matrixsdk.api.identifiers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RoomIDTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "!abc123:example.org",
        "!ABCdef456:example.org",
        "!opaque_with-chars.and~more:example.org",
        "!a:example.org",
        "!1234567890:matrix.org",
        "!abc123:localhost",
        "!abc123:example.org:8448",
        "!abc123:[2001:db8::1]",
        "!abc123:[2001:db8::1]:8448",
        "!abc123:127.0.0.1",
        "!AaBbCc123_-XyZ", // v12
        "!abc123", // v12
        "!a", // v12
        "!opaque.with-chars_and~more", // v12
      })
  void withValidStrings_ReturnRoomID(String roomId) {
    assertDoesNotThrow(() -> RoomID.create(roomId), "Exception not expected for input: " + roomId);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "abc123:example.org", // missing leading sigil
        "#abc123:example.org", // wrong sigil (alias, not id)
        "!:example.org", // empty opaque id
        "!abc123:", // empty domain
        "!abc 123:example.org", // whitespace in opaque id
        "!abc123:exa mple.org", // whitespace in domain
        "!abc123:example..org", // malformed domain
        "!abc123:-example.org", // domain label starts with hyphen
        "!abc123:example.org:99999", // port out of range
        "!abc123:[2001:db8::1", // unterminated IPv6 literal
        "",
        "!",
        "!abc123:", // trailing colon w/ empty domain
        "!abc 123", // whitespace still invalid
      })
  void withInvalidStrings_ThrowsException(String roomId) {
    assertThrows(
        IllegalArgumentException.class,
        () -> RoomID.create(roomId),
        "Exception expected for input:" + roomId);
  }

  @ParameterizedTest
  @NullSource
  void withNull_ThrowsException(String roomId) {
    assertThrows(NullPointerException.class, () -> RoomID.create(roomId));
  }

  @ParameterizedTest
  @EmptySource
  void withEmpty_ThrowsException(String roomId) {
    assertThrows(IllegalArgumentException.class, () -> RoomID.create(roomId));
  }

  @Test
  void withNull_ThrowsNPE() {
    assertThrows(NullPointerException.class, () -> RoomID.create(null));
  }
}
