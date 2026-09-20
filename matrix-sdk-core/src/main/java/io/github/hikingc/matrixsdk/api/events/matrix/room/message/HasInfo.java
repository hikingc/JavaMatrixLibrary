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

import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomAvatar;

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
