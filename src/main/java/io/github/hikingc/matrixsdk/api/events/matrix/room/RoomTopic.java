package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

public record RoomTopic(@JsonProperty("m.topic") TopicContentBlock mTopic)
    implements StateEventContent {

  public record TopicContentBlock(@JsonProperty("m.text") TextualRepresentation mText) {

    public record TextualRepresentation(String body, String mimetype) {}
  }
}
