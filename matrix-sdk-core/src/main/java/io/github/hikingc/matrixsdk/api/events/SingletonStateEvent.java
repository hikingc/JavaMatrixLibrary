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

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.events.server.state.*;

/// Interface that represents all state events which hold an empty `state_key` [String].
///
/// @param <C> a Record that represents the `content` of the event.
public sealed interface SingletonStateEvent<C extends StateEventContent> extends StateEvent<C>
    permits RoomAvatarEvent,
        RoomCanonicalAliasEvent,
        RoomCreateEvent,
        RoomEncryptionEvent,
        RoomGuestAccessEvent,
        RoomHistoryVisibilityEvent,
        RoomJoinRulesEvent,
        RoomNameEvent,
        RoomPinnedEventsEvent,
        RoomPowerLevelsEvent,
        RoomServerAclEvent,
        RoomTopicEvent {

  @Override
  default String stateKey() {
    return "";
  }
}
