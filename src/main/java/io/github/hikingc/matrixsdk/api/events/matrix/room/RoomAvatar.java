package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.HasInfo;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.ThumbnailInfo;
import java.net.URI;

/// Event content that associates a picture to a room (think of a room icon).
///
/// @param info metadata about the image referred to in `url`.
/// @param url the [URI] to the image. If this property is not present, the room has no avatar. This
///   can be useful to remove a previous room avatar.
public record RoomAvatar(AvatarInfo info, URI url) implements StateEventContent {

  /// Metadata object about the avatar icon.
  ///
  /// @param h the intended display height of the image in pixels. This may differ from the
  ///   intrinsic dimensions of the image file.
  /// @param mimetype the mimetype of the image, for example, `image/jpeg`.
  /// @param size size of the image in bytes.
  /// @param thumbnailInfo metadata about the image referred to in `thumbnail_url`.
  /// @param thumbnailUrl the [URI] (typically `mxc://`) to a thumbnail of the image.
  /// @param w the intended display width of the image in pixels. This may differ from the intrinsic
  ///   dimensions of the image file.
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
