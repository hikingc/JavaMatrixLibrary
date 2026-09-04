package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NullMarked;

/// Content information that shows the controls of whether guest users are allowed to join rooms. If
/// this event is absent, servers should act as if it is present and has the guest_access value
/// “forbidden”.
///
/// @param guestAccess whether guests can join the room.
@NullMarked
public record RoomGuestAccess(@JsonProperty(required = true) GuestAccessType guestAccess)
    implements StateEventContent {}
