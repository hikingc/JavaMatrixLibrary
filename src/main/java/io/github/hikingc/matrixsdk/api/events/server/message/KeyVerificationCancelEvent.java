package io.github.hikingc.matrixsdk.api.events.server.message;

import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.key.KeyVerificationCancel;

public record KeyVerificationCancelEvent(
    KeyVerificationCancel content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<KeyVerificationCancel> {}
