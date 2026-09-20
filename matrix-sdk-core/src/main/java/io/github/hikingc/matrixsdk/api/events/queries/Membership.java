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

import com.fasterxml.jackson.annotation.JsonProperty;

/// Membership keys used to filter membership to include or exclude in the request.
public enum Membership {
  /// Filter by `join` value. Users who have this value can send and receive events in the room
  @JsonProperty("join")
  JOIN("join"),
  /// Filter by `invite` value. Users who have this value have been invited but not yet participate.
  @JsonProperty("invite")
  INVITE("invite"),
  /// Filter by `knock` value. Users who have this value have requested membership, yet hasn't been
  /// allowed.
  @JsonProperty("knock")
  KNOCK("knock"),
  /// Filter by `leave` value. Users who have this value have left the room.
  @JsonProperty("leave")
  LEAVE("leave"),
  /// Filter by `banned` value. Users who have this value aren't allowed to join.
  @JsonProperty("ban")
  BAN("ban");

  private final String value;

  Membership(String value) {
    this.value = value;
  }

  /// The kind of membership value.
  ///
  /// @return the membership value.
  public String getValue() {
    return this.value;
  }
}
