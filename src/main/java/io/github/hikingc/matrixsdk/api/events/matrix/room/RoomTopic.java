package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

public record RoomTopic(
    @JsonProperty("m.topic") TopicContentBlock mTopic,
    @NonNull @JsonProperty(required = true) String topic)
    implements StateEventContent {

  public record TopicContentBlock(@JsonProperty("m.text") List<TextualRepresentation> mText) {

    public record TextualRepresentation(String body, String mimetype) {}
  }
}
