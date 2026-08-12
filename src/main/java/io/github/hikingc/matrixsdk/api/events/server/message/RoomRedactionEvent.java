package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomRedaction;

@JsonTypeName("m.room.redaction")
public record RoomRedactionEvent(RoomRedaction content,
                                String eventId,
                                Long originServerTs,
                                String roomId,
                                String sender,
                                String stateKey,
                                UnsignedData unsigned) implements MessageEvent<RoomRedaction> {
    /// @return the type of the event.
    @Override
    public String type() {
        return "m.room.redaction";
    }
}
