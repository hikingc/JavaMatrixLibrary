package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.CallSelectAnswer;

@JsonTypeName("m.call.select_answer")
public record CallSelectAnswerEvent(
    CallSelectAnswer content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements MessageEvent<CallSelectAnswer> {

  @Override
  public String type() {
    return "m.call.select_answer";
  }
}
