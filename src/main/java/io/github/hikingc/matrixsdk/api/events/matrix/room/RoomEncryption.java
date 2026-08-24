package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import org.jspecify.annotations.NonNull;

/// Content information that defines how messages sent in this room should be encrypted.
///
/// @param algorithm the encryption algorithm to be used to encrypt messages sent in this room.
/// @param rotationPeriodMs how long the session should be used before changing it. `604800000` (a week) is the recommended default.
/// @param rotationPeriodMsgs how many messages should be sent before changing the session. `100` is the recommended default.
public record RoomEncryption(
    @NonNull @JsonProperty(required = true) String algorithm,
    Integer rotationPeriodMs,
    Integer rotationPeriodMsgs)
    implements StateEventContent {}
