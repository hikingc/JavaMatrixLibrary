package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;
import org.jspecify.annotations.NullMarked;

/// Content information of "pinned" events in a room for other participants to review later. **The
/// order of the pinned events is guaranteed and based upon the order supplied in the event.**
///
/// Clients should be aware that the current user may not be able to see some of the events pinned
/// due to visibility settings in the room.
///
/// Clients are responsible for determining if a particular event in the pinned list is displayable,
/// and have the option to not display it if it cannot be pinned in the client.
///
/// @param pinned an ordered [List] of event IDs to pin.
@NullMarked
public record RoomPinnedEvents(@JsonProperty(required = true) List<String> pinned)
    implements StateEventContent {}
