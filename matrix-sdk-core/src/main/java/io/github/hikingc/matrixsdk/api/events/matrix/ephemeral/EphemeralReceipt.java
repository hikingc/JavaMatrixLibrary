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
package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.Map;

/// Content information about new receipts.
///
/// @param receipts the mapping of [event ID][EventID] to a collection of [receipts][Receipt] for
///   this event ID. The event ID is the ID of the event being acknowledged and _not_ an ID for the
///   receipt itself.
public record EphemeralReceipt(@JsonValue Map<EventID, EventReceipts> receipts)
    implements EphemeralContent {
  /// Information about receipts.
  ///
  /// @param mRead a collection of users who have sent `m.read` receipts for this event. The string
  ///   key is the [user ID][UserID] the receipt belongs to.
  /// @param mReadPrivate Similar to `m.read`, the users who have sent `m.read.private` receipts for
  ///   this event. Due to the nature of private read receipts, this should only ever have the
  ///   current user’s ID.
  public record EventReceipts(
      @JsonProperty("m.read") Map<UserID, Receipt> mRead,
      @JsonProperty("m.read.private") Map<UserID, Receipt> mReadPrivate) {}

  /// Information about the receipt
  ///
  /// @param threadId the thread root’s event ID (or main) for which thread this receipt is intended
  ///   to be under. If not specified, the read receipt is _unthreaded_ (default).
  /// @param ts the timestamp the receipt was sent at.
  public record Receipt(String threadId, Integer ts) {}
}
