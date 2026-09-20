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
package io.github.hikingc.matrixsdk.api.events.queries;

/// Chronological order keys used to inform the server on how should the messages be ordered.
public enum ChronologicalDirection {
  /// Look forwards in time (from oldest to newest messages).
  CHRONOLOGICAL_ORDER("f"),
  /// Look backwards in time (from newest to oldest messages).
  REVERSE_CHRONOLOGICAL_ORDER("b");

  private final String value;

  ChronologicalDirection(String value) {
    this.value = value;
  }

  /// The query value ('f' or 'b').
  ///
  /// @return the query value.
  public String getValue() {
    return this.value;
  }
}
