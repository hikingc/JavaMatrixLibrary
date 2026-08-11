package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomCanonicalAlias;

@JsonTypeName("m.room.canonical.alias")
public record RoomCanonicalAliasEvent(
    RoomCanonicalAlias content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomCanonicalAlias> {

  @Override
  public String type() {
    return "m.room.canonical_alias";
  }
}
