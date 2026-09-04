package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Content information about the first event in a room. It acts as the root of all other events.
///
/// **This event content cannot be changed**
///
/// @param additionalCreators Starting with room version 12, the other user IDs to consider as
///   creators for the room in addition to the sender of this event. Each string MUST be a valid
///   user ID for the room version.
///
///   When not present or empty, the `sender` of the event is the only creator.
///
///   In room versions 1 through 11, this field serves no purpose and is not validated. Clients
///   SHOULD NOT attempt to parse or understand this field in these room versions.
///
///   Note: Because `creator` was removed in room version 11, the field is not used to determine
///   which user(s) are room creators in room version 12 and beyond either.
/// @param mFederate the `user_id` of the room creator. **Required** for, and only present in, room
///   versions 1 - 10. Starting with room version 11 the event
///   [`sender`][io.github.hikingc.matrixsdk.api.events.ClientEvent#sender()] should be used
///   instead.
/// @param predecessor a reference to the room this room replaces, if the previous room was
///   upgraded.
/// @param roomVersion the version of the room. Defaults to "1" if the key does not exist.
/// @param type optional room type to denote a room’s intended function outside traditional
///   conversation.
///
///   Unspecified room types are possible using Namespaced Identifiers.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#types">Room types in the
///   spec.</a>
public record RoomCreate(
    List<String> additionalCreators,
    @JsonProperty(namespace = "m.federate") Boolean mFederate,
    PreviousRoom predecessor,
    String roomVersion,
    String type)
    implements StateEventContent {
  /// Reference room object.
  ///
  /// @param eventId the event ID of the last known event in the old room, if known.
  ///
  ///   If not set, clients SHOULD search for the
  ///   [`m.room.tombstone`][io.github.hikingc.matrixsdk.api.events.server.state.RoomTombstoneEvent]
  ///   state event to navigate to when directing the user to the old room (potentially after
  ///   joining the room, if requested by the user). This field became deprecated in v1.16
  /// @param roomId the ID of the old room.
  @NullMarked
  public record PreviousRoom(
      @Nullable EventID eventId, @JsonProperty(required = true) RoomID roomId) {}
}
