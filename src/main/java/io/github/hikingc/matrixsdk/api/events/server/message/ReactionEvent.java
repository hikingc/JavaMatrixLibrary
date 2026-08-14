package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.Reaction;

@JsonTypeName("m.reaction")
public record ReactionEvent(
    Reaction content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<Reaction> {}
