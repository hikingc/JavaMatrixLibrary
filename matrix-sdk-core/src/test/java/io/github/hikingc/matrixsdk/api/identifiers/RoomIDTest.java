/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.api.identifiers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
  @DisplayName("Check valid RoomID strings")
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
  @DisplayName("Check invalid RoomID strings")
  void withInvalidStrings_ThrowsException(String roomId) {
    assertThrows(
        IllegalArgumentException.class,
        () -> RoomID.create(roomId),
        "Exception expected for input:" + roomId);
  }

  @Test
  @DisplayName("Check RoomID null throws")
  void withNull_ThrowsNPE() {
    assertThrows(NullPointerException.class, () -> RoomID.create(null));
  }
}
