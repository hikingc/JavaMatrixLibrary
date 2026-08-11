package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.SingletonStateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.RoomTopic;

@JsonTypeName("m.room.topic")
public record RoomTopicEvent(
    RoomTopic content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements SingletonStateEvent<RoomTopic> {

  @Override
  public String type() {
    return "m.room.topic";
  }
}
