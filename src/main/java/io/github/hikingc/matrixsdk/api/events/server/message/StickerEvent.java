package io.github.hikingc.matrixsdk.api.events.server.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.MessageEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.Sticker;

@JsonTypeName("m.sticker")
public record StickerEvent(
    Sticker content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    UnsignedData unsigned)
    implements MessageEvent<Sticker> {
  /// @return the type of the event.
  @Override
  public String type() {
    return "m.sticker";
  }
}
