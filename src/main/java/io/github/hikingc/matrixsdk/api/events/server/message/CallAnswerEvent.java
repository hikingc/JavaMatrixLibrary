package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.call.CallAnswer;

@JsonTypeName("m.call.answer")
public record CallAnswerEvent(
    CallAnswer content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements MessageEvent<CallAnswer> {
  @Override
  public String type() {
    return "m.call.answer";
  }
}
