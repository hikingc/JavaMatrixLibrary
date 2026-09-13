package io.github.hikingc.matrixsdk.api.identifiers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomAliasTest {

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @ValueSource(
      strings = {
        "#general:example.org",
        "#General:example.org",
        "#room.name:example.org",
        "#room_name:example.org",
        "#room-name:example.org",
        "#room+name:example.org",
        "#123room:example.org",
        "#a:example.org",
        "#general:localhost",
        "#general:matrix.example.org",
        "#general:example.org:8448",
        "#general:[2001:db8::1]",
        "#general:127.0.0.1"
      })
  @DisplayName("Check valid RoomAliasID strings")
  void withValidStrings_ReturnRoomAlias(String alias) {
    assertDoesNotThrow(() -> RoomAlias.create(alias), "Exception not expected for input: " + alias);
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @ValueSource(
      strings = {
        "general:example.org", // missing leading sigil
        "!general:example.org", // wrong sigil (id, not alias)
        "#:example.org", // empty localpart
        "#general", // missing colon/domain
        "#general:", // empty domain
        "#gen eral:example.org", // whitespace in localpart
        "#general:exa mple.org", // whitespace in domain
        "#general:example..org", // malformed domain
        "#general:-example.org", // domain label starts with hyphen
        "#general:example.org:99999", // port out of range
        "#general:[2001:db8::1", // unterminated IPv6 literal
        "",
        "#"
      })
  @DisplayName("Check invalid RoomAliasID strings")
  void withInvalidStrings_ThrowsException(String alias) {
    assertThrows(
        IllegalArgumentException.class,
        () -> RoomAlias.create(alias),
        "Exception expected for input:" + alias);
  }

  @Test
  @DisplayName("Check RoomAliasID null throws")
  void withNull_ThrowsNPE() {
    assertThrows(NullPointerException.class, () -> RoomAlias.create(null));
  }
}
