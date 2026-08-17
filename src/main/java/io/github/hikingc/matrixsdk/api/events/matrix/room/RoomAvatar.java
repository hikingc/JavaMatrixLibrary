package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.HasInfo;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.ThumbnailInfo;
import java.net.URI;

public record RoomAvatar(AvatarInfo info, URI url) implements StateEventContent {

  public record AvatarInfo(
      Integer h,
      String mimetype,
      Integer size,
      ThumbnailInfo thumbnailInfo,
      URI thumbnailUrl,
      Integer w)
      implements HasInfo { // Just like messages/Image, perhaps extract into an interface?
  }
}
