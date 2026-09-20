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

/// These are events which update the metadata state of the room (e.g. room topic, room membership
/// etc.). State is keyed by a tuple of event type and a state_key. State in the room with the same
/// key-tuple will be overwritten.
///
/// @param <C>
public sealed interface StateEvent<C extends StateEventContent> extends ClientEvent<C>
    permits SingletonStateEvent,
        RoomMemberEvent,
        RoomThirdPartyInviteEvent,
        RoomTombstoneEvent,
        SpaceChildEvent,
        SpaceParentEvent { // Stripped state events are missing from this tree...

  String stateKey();
}
