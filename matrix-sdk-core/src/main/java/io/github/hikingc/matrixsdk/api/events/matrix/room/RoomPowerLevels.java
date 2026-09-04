package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.HashMap;
import java.util.Map;

/// Content information about the power level required to perform certain actions.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#mroompower_levels">Details and
///   notes about power levels in the spec.</a>
///
/// @param ban the level required to ban a user. Defaults to 50 if unspecified.
/// @param events the level required to send specific event types. This is a [Map] from event type
///   to power level required.
///
///   Though not a default, when the server sends the initial power levels event during [room
///   creation][io.github.hikingc.matrixsdk.api.Room#create(io.github.hikingc.matrixsdk.api.rooms.InitialRoomConfiguration)]
///   in room versions 12 and higher, the
///   [`m.room.tombstone`][io.github.hikingc.matrixsdk.api.events.server.state.RoomTombstoneEvent]
///   event MUST be explicitly defined and given a power level higher than state_default. For
///   example, power level 150. Clients may override this using the described
///   `power_level_content_override` field.
///
/// @param eventsDefault the default level required to send message events. Can be overridden by the
///   `events` key. Defaults to 0 if unspecified.
/// @param invite the level required to invite a user. Defaults to 0 if unspecified.
/// @param kick the level required to kick a user. Defaults to 50 if unspecified.
/// @param notifications the power level requirements for specific notification types. This is a
///   [Map][Notifications#of(Map)] from `key` to power level for that notifications key.
/// @param redact the level required to redact an event sent by another user. Defaults to 50 if
///   unspecified.
/// @param stateDefault the default level required to send state events. Can be overridden by the
///   events key. Defaults to 50 if unspecified.
/// @param users the power levels for specific users. This is a mapping from `user_id` to power
///   level for that user.
/// @param usersDefault the power level for users in the room whose user_id is not mentioned in the
///   users key. Defaults to 0 if unspecified.
public record RoomPowerLevels(
    Integer ban,
    Map<String, Integer> events,
    Integer eventsDefault,
    Integer invite,
    Integer kick,
    Notifications notifications,
    Integer redact,
    Integer stateDefault,
    Map<UserID, Integer> users,
    Integer usersDefault)
    implements StateEventContent {
  /// Power level object map about notification types.
  ///
  /// @param room the level required to trigger a @room notification. Defaults to 50 if unspecified.
  /// @param otherProperties any other property.
  public record Notifications(
      Integer room,
      Map<String, Object> otherProperties // this type of payload is used in UserProfile too.
      ) {
    /// Deserialization helper to accommodate additional unknown fields.
    ///
    /// @param raw input key-values from a response.
    /// @return deserialized [Notifications] with corresponding values.
    @JsonCreator
    public static Notifications of(Map<String, Object> raw) {
      Map<String, Object> copy = new HashMap<>(raw);
      Integer room = (Integer) copy.remove("room");
      return new Notifications(room, Map.copyOf(copy));
    }
  }
}
