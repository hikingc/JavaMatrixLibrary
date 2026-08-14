package io.github.hikingc.matrixsdk.api.events.matrix.room.messages;

import java.net.URI;

/// Additional file information referred about the image in the `url` field.
///
/// @param h the intended display height of the image in pixels. This may differ from the intrinsic
///   dimensions of the image file.
/// @param w the intended display width of the image in pixels. This may differ from the intrinsic
///   dimensions of the image file.
/// @param isAnimated when set to true, the image SHOULD be assumed to be animated. Leave unset if
///   unable to determine.
/// @param mimetype the mimetype of the image.
/// @param size the size of the image in bytes.
/// @param thumbnailFile information on the encrypted thumbnail file. Currently not supported.
/// @param thumbnailInfo metadata about the image referred to in `thumbnailUrl`.
/// @param thumbnailUrl the URL to the thumbnail of the file. Only present if the thumbnail is
///   unencrypted.
public record ImageInfo(
    Integer h,
    Integer w,
    Integer size,
    String mimetype,
    Boolean isAnimated,
    EncryptedFile thumbnailFile,
    ThumbnailInfo thumbnailInfo,
    URI thumbnailUrl)
    implements HasInfo, HasThumbnail, HasArea {}
