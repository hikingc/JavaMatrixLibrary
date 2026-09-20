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
package io.github.hikingc.matrixsdk.api.filters;

import java.util.List;

/// Signals what to filter by from an event. Recommended to use in addition with [RoomEventFilter]
/// for more granular filters.
///
/// @param limit The maximum number of events to return, **must be an integer greater than 0**.
///
///   Servers should apply a default value, and impose a maximum value to avoid resource exhaustion.
/// @param notSenders a list of sender IDs to exclude. If this list is absent then no senders are
///   excluded. A matching sender will be excluded even if it is listed in the senders filter.
/// @param notTypes a list of event types to exclude. If this list is absent then no event types are
///   excluded. A matching type will be excluded even if it is listed in the types filter. A * can
///   be used as a wildcard to match any sequence of characters.
/// @param senders a list of senders IDs to include. If this list is absent then all senders are
///   included.
/// @param types a list of event types to include. If this list is absent then all event types are
///   included. A "*" can be used as a wildcard to match any sequence of characters.
public record EventFilter(
    Integer limit,
    List<String> notSenders,
    List<String> notTypes,
    List<String> senders,
    List<String> types) {
  public EventFilter {
    if (limit <= 0) {
      throw new IllegalArgumentException("Field limit must be set above 0.");
    }
  }
}
