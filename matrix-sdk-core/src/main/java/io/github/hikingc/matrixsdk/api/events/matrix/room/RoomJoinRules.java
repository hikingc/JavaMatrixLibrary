package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Content information about join rules.
///
/// @param allow for `restricted` rooms, the conditions the user will be tested against. The user
///   needs only to satisfy one of the conditions to join the `restricted` room. If the user fails
///   to meet any condition, or the condition is unable to be confirmed as satisfied, then the user
///   requires an invitation to join the room. Improper or no `allow` conditions on a `restricted`
///   join rule imply the room is effectively invite-only (no conditions can be satisfied).
/// @param joinRule the type of rules used for users wishing to join this room.
public record RoomJoinRules(
    List<AllowCondition> allow, @NonNull @JsonProperty(required = true) String joinRule)
    implements StateEventContent {

  /// Information about the allow condition.
  ///
  /// @param roomId Required if type is `m.room_membership`. The [RoomID] to check the user’s
  ///   membership against. If the user is joined to this room, they satisfy the condition and thus
  ///   are permitted to join the `restricted` room.
  public record AllowCondition(RoomID roomId) {

    /// The type of condition:
    ///
    /// - m.room_membership - the user satisfies the condition if they are joined to the referenced
    ///   room.
    ///
    /// @return always `m.room_membership`.
    @JsonProperty("type")
    public String type() {
      return "m.room_membership";
    }
  }
}
