package io.github.hikingc.matrixsdk.api.events.matrix;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.ImageInfo;
import java.net.URI;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record Sticker(
    @JsonProperty(required = true) String body,
    @JsonProperty(required = true) ImageInfo info,
    @JsonProperty(required = true) URI url)
    implements MessageEventContent {}
