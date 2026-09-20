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

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomMessage;
import java.net.URI;

/// Message that represents a file resource. If the type of file is known, it's better to use their
/// dedicated event, such as for [videos][VideoContent], [images][ImageContent], or
/// [audio][AudioContent].
///
/// @param body the filename of the original upload if `filename` is unset or identical to it;
///   otherwise, a caption for the file.
/// @param file information on the encrypted file, as specified in End-to-end encryption. Required
///   if the file is encrypted.
/// @param filename the original filename of the uploaded file.
/// @param format the format used in `formattedBody`. Required if `formattedBody` is specified;
///   currently only `org.matrix.custom.html` is supported.
/// @param formattedBody the formatted version of `body`, when it acts as a caption. Required if
///   `format` is specified.
/// @param info metadata for the audio clip referred to by `url`.
/// @param url required if the file is unencrypted.
@JsonTypeName("m.file")
public record FileContent(
    String body,
    EncryptedFile file,
    String filename,
    String format,
    String formattedBody,
    FileInfo info,
    URI url)
    implements RoomMessage {

  @Override
  public String msgtype() {
    return "m.file";
  }

  /// Additional file information referred about the file in the `url` field.
  ///
  /// @param mimetype the mimetype of the file.
  /// @param size the size of the file in bytes.
  /// @param thumbnailFile information on the encrypted thumbnail file. Currently not supported.
  /// @param thumbnailInfo metadata about the image referred to in `thumbnailUrl`.
  /// @param thumbnailUrl the URL to the thumbnail of the file. Only present if the thumbnail is
  ///   unencrypted.
  public record FileInfo(
      String mimetype,
      Integer size,
      EncryptedFile thumbnailFile,
      ThumbnailInfo thumbnailInfo,
      URI thumbnailUrl)
      implements HasInfo, HasThumbnail {}
}
