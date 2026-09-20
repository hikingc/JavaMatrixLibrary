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
package io.github.hikingc.matrixsdk.api.rooms;

import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.Objects;

/// Required values to be supplied to actions like banning or kicking.
///
/// @param reason The reason of the expulsion, the target will receive this message.
/// @param userId The id of the target to expel.
public record RoomMembershipRequest(String reason, UserID userId) {

  /// Compact constructor designed to validate nullity.
  ///
  /// @param reason The reason of the expulsion, the target will receive this message.
  /// @param userId The id of the target to expel.
  /// @throws NullPointerException if either value is null
  public RoomMembershipRequest {
    Objects.requireNonNull(reason, "roomId cannot be null");
    Objects.requireNonNull(userId, "roomAlias cannot be null");
  }
}
