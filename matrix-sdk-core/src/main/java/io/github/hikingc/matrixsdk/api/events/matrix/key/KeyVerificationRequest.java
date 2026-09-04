package io.github.hikingc.matrixsdk.api.events.matrix.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Content information about requesting a key verification using to-device messaging. When
/// requesting a key verification in a room, a
/// [`m.room.message`][io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage] should be
/// used, with `m.key.verification.request` as `msgtype`.
///
/// @param fromDevice the device ID which is initiating the request.
/// @param methods the verification methods supported by the sender.
/// @param timestamp required when sent as a to-device message. The POSIX timestamp in milliseconds
///   for when the request was made. If the request is in the future by more than 5 minutes or more
///   than 10 minutes in the past, the message should be ignored by the receiver.
/// @param transactionId required when sent as a to-device message. An opaque identifier for the
///   verification request. Must be unique with respect to the devices involved.

public record KeyVerificationRequest(
    @NonNull @JsonProperty(required = true) String fromDevice,
    @NonNull @JsonProperty(required = true) List<String> methods,
    Long timestamp,
    String transactionId)
    implements MessageEventContent {}
