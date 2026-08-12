package io.github.hikingc.matrixsdk.api.events.matrix.space;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

public record SpaceParent(
    Boolean canonical, @NonNull @JsonProperty(required = true) List<String> via)
    implements StateEventContent {}
