package io.github.hikingc.matrixsdk.api.events.matrix.room.message;

import java.net.URI;

/// Marks event content that includes thumbnail metadata such as E2E metadata their width, size and
/// height and url `info` object.
public sealed interface HasThumbnail
    permits FileContent.FileInfo, LocationContent.LocationInfo, ImageInfo, VideoContent.VideoInfo {
  /// Encrypted file metadata information.
  ///
  /// @return a [EncryptedFile].
  EncryptedFile thumbnailFile();

  /// Metadata about the resource referred to in thumbnail\_url.
  ///
  /// @return a [ThumbnailInfo].
  ThumbnailInfo thumbnailInfo();

  /// The URL to the thumbnail of the resource. Only present if the thumbnail is unencrypted.
  ///
  /// @return an [URI].
  URI thumbnailUrl();
}
