package io.github.hikingc.matrixsdk.api.events.server.message;

import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.key.KeyVerificationKey;

public record KeyVerificationKeyEvent(
    KeyVerificationKey content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<KeyVerificationKey> {}
