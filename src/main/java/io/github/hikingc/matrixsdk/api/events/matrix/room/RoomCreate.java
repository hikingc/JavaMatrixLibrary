package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

import java.util.List;

public record RoomCreate(
    List<String> additionalCreators,
    String creator,
    @JsonProperty(namespace = "m.federate") Boolean mFederate,
    PreviousRoom predecessor,
    String roomVersion,
    String type)
    implements StateEventContent {

  public record PreviousRoom(String eventId, String roomId) {}
}
