package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.util.List;
import org.jspecify.annotations.NonNull;

public record RoomCreate(
    List<String> additionalCreators,
    String creator,
    @JsonProperty(namespace = "m.federate") Boolean mFederate,
    PreviousRoom predecessor,
    String roomVersion,
    String type)
    implements StateEventContent {

  public record PreviousRoom(
      EventID eventId, @NonNull @JsonProperty(required = true) RoomID roomId) {}
}
