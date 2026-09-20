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
import io.github.hikingc.matrixsdk.api.events.server.UnknownEvent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

/// Interface that enforces fields required by both State and Message events when the event is
/// retrieved from the server via the Client-Server API, or sent to an Application Service via the
/// Application Services API.
///
/// @param <T> the `m.` type event.
/// @see <a href="https://spec.matrix.org/latest/client-server-api/#room-event-format">The room
///   event format as defined in the specification.</a>
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    defaultImpl = UnknownEvent.class)
// This interface CANNOT permit anything other than these two.
public sealed interface ClientEvent<T> permits StateEvent, MessageEvent, UnknownEvent {
  /// a
  ///
  /// @return the body of this event, as created by the user which sent it.
  T content();

  /// a
  ///
  /// @return the globally unique identifier for this event.
  EventID eventId();

  /// a
  ///
  /// @return timestamp (in milliseconds since the Unix epoch) on originating homeserver when this
  ///   event was sent.
  Long originServerTs();

  /// a
  ///
  /// @return the ID of the room associated with this event.
  RoomID roomId();

  /// a
  ///
  /// @return contains the fully-qualified ID of the user who sent this event.
  UserID sender();

  /// a
  ///
  /// @return the type of the event.
  String type();

  /// a
  ///
  /// @return optional extra information about the event.
  UnsignedData unsigned();
}
