package io.github.hikingc.matrixsdk.api.events.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.content.roommessages.ImageInfo;
import java.net.URI;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record Sticker(
    @NonNull @JsonProperty(required = true) String body,
    @NonNull @JsonProperty(required = true) ImageInfo info,
    @NonNull @JsonProperty(required = true) URI url) implements MessageEventContent {}
