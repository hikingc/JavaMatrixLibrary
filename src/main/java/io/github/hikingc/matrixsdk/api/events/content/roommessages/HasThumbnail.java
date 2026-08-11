package io.github.hikingc.matrixsdk.api.events.content.roommessages;

import io.github.hikingc.matrixsdk.api.events.crypto.EncryptedFile;

import java.net.URI;

/// Marks event content that includes thumbnail metadata such as E2E metadata their width, size and
/// height and url `info` object.
public sealed interface HasThumbnail
    permits FileContent.FileInfo,
        LocationContent.LocationInfo,
        ImageInfo,
        VideoContent.VideoInfo {
  /// @return not implemented yet.
  EncryptedFile thumbnailFile();

  /// @return metadata about the resource referred to in thumbnail\_url
  ThumbnailInfo thumbnailInfo();

  /// @return the URL to the thumbnail of the resource. Only present if the thumbnail is
  ///   unencrypted.
  URI thumbnailUrl();
}
