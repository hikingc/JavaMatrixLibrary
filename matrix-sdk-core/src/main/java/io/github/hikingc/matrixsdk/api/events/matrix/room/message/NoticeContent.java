package io.github.hikingc.matrixsdk.api.events.matrix.room.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import org.jspecify.annotations.NonNull;

@JsonTypeName("m.notice")
public record NoticeContent(
    @NonNull @JsonProperty(required = true) String body, String format, String formattedBody)
    implements RoomMessage {
  @Override
  public String msgtype() {
    return "m.notice";
  }
}
