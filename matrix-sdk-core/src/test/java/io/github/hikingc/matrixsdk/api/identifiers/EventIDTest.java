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

class EventIDTest {

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @ValueSource(
      strings = {
        // v3+ reference-hash shape
        "$AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        "$Rqnc-F-dvnEYJTyHq_iKxU2bZ1CI9dSaz3jRoiQ-fXE",
        "$acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA",
        "$LWXstUyAjMr8vBiVjTMH_hEcnKMhc0zVi52gxHYzc-4",
        "$1c-AYXvOG3AH0z9OTfHktZ4b6l3f1uK1Wv4h5CkQY9U",
        // legacy v1/v2 shape
        "$acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA:matrix.org",
        "$event1:example.com",
        "$143273582443PhrSn:example.org",
        // opaque content is allowed to contain "unusual" characters per spec
        "$acR1l0raRc2h8DzKlR4E9RAxwbrIY8v/4V+1kfBGCiA", // non-base64url chars, still opaque
        "$has spaces in it",
        "$has\nnewline",
        "$emoji🎉event",
        "$has\"quote",
        "$ " // single space after sigil is still non-empty content
      })
  @DisplayName("Check valid EventID strings")
  void withValidStrings_ReturnEventID(String id) {
    assertDoesNotThrow(() -> EventID.create(id), "Exception not expected for input: " + id);
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @ValueSource(
      strings = {
        "acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA", // missing sigil
        "@acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA", // wrong sigil (User ID)
        "!acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA", // wrong sigil (Room ID)
        "#acR1l0raRc2h8DzKlR4E9RAxwbrIY8v_4V-1kfBGCiA", // wrong sigil (Room Alias)
        " ", // no sigil at all, just whitespace
        "$", // sigil present but zero content after it
        ""
      })
  @DisplayName("Check invalid EventID strings")
  void withInvalidStrings_ThrowsException(String id) {
    assertThrows(
        IllegalArgumentException.class,
        () -> EventID.create(id),
        "Exception expected for input: " + id);
  }

  @Test
  @DisplayName("Check EventID null throws")
  void withNull_ThrowsException() {
    assertThrows(NullPointerException.class, () -> EventID.create(null));
  }
}
