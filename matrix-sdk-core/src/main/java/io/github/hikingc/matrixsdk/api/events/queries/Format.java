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

/// Format keys used to define what format should states return as.
public enum Format {
  /// Returns only content of the state event.
  CONTENT("content"),
  /// Returns the entire event in the usual format suitable for clients, including fields like event
  /// ID, sender and timestamp.
  EVENT("event");

  private final String value;

  Format(String value) {
    this.value = value;
  }

  /// The kind of format value.
  ///
  /// @return the format value.
  public String getValue() {
    return this.value;
  }
}
