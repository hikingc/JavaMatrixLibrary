package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.call.CallSelectAnswer;

@JsonTypeName("m.call.select_answer")
public record CallSelectAnswerEvent(
    CallSelectAnswer content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<CallSelectAnswer> {}
