package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.ServerACL;

@JsonTypeName("m.room.server_acl")
public record RoomServerACLEvent(
    ServerACL content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<ServerACL> {

  /// @return the type of the event.
  @Override
  public String type() {
    return "m.room.server_acl";
  }
}
