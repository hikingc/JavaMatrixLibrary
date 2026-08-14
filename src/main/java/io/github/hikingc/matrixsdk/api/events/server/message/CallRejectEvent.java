package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.call.CallReject;

@JsonTypeName("m.call.reject")
public record CallRejectEvent(
    CallReject content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<CallReject> {

}
