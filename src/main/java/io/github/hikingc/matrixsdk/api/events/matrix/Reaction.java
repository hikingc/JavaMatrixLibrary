package io.github.hikingc.matrixsdk.api.events.matrix;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;

public record Reaction(@JsonProperty("m.relates_to") ReactionRelatesTo mRelatesTo)
    implements MessageEventContent {
  public record ReactionRelatesTo(EventID eventId,
                                  String key) {
    @JsonGetter("relType")
    public String getRelType() {
      return "m.reaction";
    }
  }
}
