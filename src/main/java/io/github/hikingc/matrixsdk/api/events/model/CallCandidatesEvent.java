package io.github.hikingc.matrixsdk.api.events.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.content.CallCandidates;

@JsonTypeName("m.call.candidates")
public record CallCandidatesEvent(
    CallCandidates content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements MessageEvent<CallCandidates> {

  @Override
  public String type() {
    return "m.call.candidates";
  }
}
