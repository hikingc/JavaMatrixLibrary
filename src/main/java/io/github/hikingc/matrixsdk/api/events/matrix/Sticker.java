package io.github.hikingc.matrixsdk.api.events.matrix;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.room.messages.ImageInfo;
import java.net.URI;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record Sticker(
    @NonNull @JsonProperty(required = true) String body,
    @NonNull @JsonProperty(required = true) ImageInfo info,
    @NonNull @JsonProperty(required = true) URI url) implements MessageEventContent {}
