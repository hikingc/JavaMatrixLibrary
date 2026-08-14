package io.github.hikingc.matrixsdk.api.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// This interface CANNOT permit anything other than these two.
public sealed interface ClientEvent<T> permits StateEvent, MessageEvent {

  /// @return the body of this event, as created by the user which sent it.
  T content();

  /// @return the globally unique identifier for this event.
  EventID eventId();

  /// @return timestamp (in milliseconds since the Unix epoch) on originating homeserver when this
  ///   event was sent.
  Long originServerTs();

  /// @return the ID of the room associated with this event.
  RoomID roomId();

  /// @return contains the fully-qualified ID of the user who sent this event.
  UserID sender();

  /// @return the type of the event.
  String type();

  /// @return optional extra information about the event.
  UnsignedData unsigned();
}
