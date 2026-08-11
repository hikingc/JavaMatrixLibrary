package io.github.hikingc.matrixsdk.api.events.content.roommessages;

import io.github.hikingc.matrixsdk.api.events.content.RoomAvatar;

/// Marks event content that includes file metadata such as a MIME type and size in bytes, as
/// described by the Matrix specification's `info` object.
public sealed interface HasInfo
    permits RoomAvatar.AvatarInfo,
        AudioContent.AudioInfo,
        FileContent.FileInfo,
        ImageInfo,
        ThumbnailInfo,
        VideoContent.VideoInfo {
  /// @return the mimetype of the corresponding input resource
  String mimetype();

  /// @return the size of the input resource in bytes.
  Integer size();
}
