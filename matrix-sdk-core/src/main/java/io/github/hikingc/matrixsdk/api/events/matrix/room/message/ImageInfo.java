/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.api.events.matrix.room.message;

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
