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
package io.github.hikingc.matrixsdk.api.rooms.queries;

import com.fasterxml.jackson.annotation.JsonProperty;

/// The room type keys used to tell the client what available preset should the server create the
/// room with.
public enum CreationRoomType {

  /// Recommended to use this to configure when you want to make 1 to 1 conversations.
  @JsonProperty("private_chat")
  PRIVATE_CHAT,
  /// Same as [#PRIVATE_CHAT] except all invitees are given the same power level as the room
  // creator.
  @JsonProperty("trusted_private_chat")
  TRUSTED_PRIVATE_CHAT,
  /// For public access, unlike the other types, choosing this will forbid guest access.
  @JsonProperty("public_chat")
  PUBLIC_CHAT
}
