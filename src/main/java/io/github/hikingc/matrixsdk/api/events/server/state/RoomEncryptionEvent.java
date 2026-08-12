package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomEncryption;

@JsonTypeName("m.room.encryption")
public record RoomEncryptionEvent(
    RoomEncryption content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomEncryption> {

  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.encryption";
  }
}
