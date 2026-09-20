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
package io.github.hikingc.matrixsdk.api.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.events.server.ephemeral.*;

/// A minimal event with no room, sender, or metadata context. The Matrix specification returns
/// these events at [io.github.hikingc.matrixsdk.api.events.sync.Sync]
///
/// Filtering ephemeral events is possible with the use of
/// [filters][io.github.hikingc.matrixsdk.api.Filter] using [filter
/// definitions][io.github.hikingc.matrixsdk.api.filters.FilterDefinition#room()]
///
/// @param <T> the `m.` type event.
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public sealed interface EphemeralEvent<T extends EphemeralContent>
    permits DirectEvent, FullyReadEvent, PresenceEvent, ReceiptEvent, TagEvent, TypingEvent {
  /// The event content, shape depends on `type`.
  ///
  /// @return the event content.
  T content();

  /// The event type, for example, `m.typing`.
  ///
  /// @return the event type.
  String type();
}
