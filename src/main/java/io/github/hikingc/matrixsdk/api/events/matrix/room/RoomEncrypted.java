package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import io.github.hikingc.matrixsdk.services.utils.deserializers.CiphertextDeserializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public record RoomEncrypted(
    @NonNull @JsonProperty(required = true) String algorithm,
    @NonNull @JsonDeserialize(using = CiphertextDeserializer.class) @JsonProperty(required = true)
    Ciphertext ciphertext,
    String sessionId)
    implements MessageEventContent {}
