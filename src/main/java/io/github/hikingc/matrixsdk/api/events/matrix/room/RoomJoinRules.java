package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record RoomJoinRules(
        List<AllowCondition> allow, @NonNull @JsonProperty(required = true) String joinRule)
    implements StateEventContent {

  public record AllowCondition(
      RoomID roomId, @NonNull @JsonProperty(required = true) String type) {}
}
