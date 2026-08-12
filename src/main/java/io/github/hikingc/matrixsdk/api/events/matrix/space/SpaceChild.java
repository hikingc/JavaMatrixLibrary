package io.github.hikingc.matrixsdk.api.events.matrix.space;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;

public record SpaceChild(String order, Boolean suggested, List<String> via)
    implements StateEventContent {}
