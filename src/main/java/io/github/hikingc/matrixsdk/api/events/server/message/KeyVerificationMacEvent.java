package io.github.hikingc.matrixsdk.api.events.server.message;

import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.key.KeyVerificationMac;

public record KeyVerificationMacEvent(
    KeyVerificationMac content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String type,
    UnsignedData unsigned)
    implements MessageEvent<KeyVerificationMac> {}
