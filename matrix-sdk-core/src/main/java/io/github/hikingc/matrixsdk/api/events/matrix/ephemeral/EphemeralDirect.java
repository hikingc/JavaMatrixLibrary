package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.List;
import java.util.Map;

/// Content information of a [Map] of which rooms are considered ‘direct’ rooms for specific users is
/// kept in `account_data` in an event of type [`m.direct`][EphemeralDirect]. The keys are the [User
/// IDs][UserID] and values are lists of room ID [Strings][String] of the ‘direct’ rooms for that
/// user ID.
///
/// @apiNote The Room IDs are not serialized into [Room
///   IDs][io.github.hikingc.matrixsdk.api.identifiers.RoomID].
///
/// @param directs this field is the mapping of user ID to a list of room IDs of the ‘direct’ rooms
///   for that user ID.
public record EphemeralDirect(@JsonValue Map<UserID, List<String>> directs)
    implements EphemeralContent {}
