package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.Tombstone;

@JsonTypeName("m.room.tombstone")
public record RoomTombstoneEvent(
    Tombstone content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    UnsignedData unsigned)
    implements StateEvent<Tombstone> {
  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.tombstone";
  }
}
