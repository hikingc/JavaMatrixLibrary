package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.Reaction;

@JsonTypeName("m.reaction")
public record ReactionEvent(
    Reaction content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    UnsignedData unsigned)
    implements MessageEvent<Reaction> {

  @Override
  public String type() {
    return "m.reaction";
  }
}
