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

/// This type of message represents a real-world location.
///
/// @param body the filename of the original upload if `filename` is unset or identical to it;
///   otherwise, a caption for the image.
/// @param info metadata for the audio clip referred to by `url`.
/// @param geoUri A geo URI (RFC5870) representing this location.
@JsonTypeName("m.location")
public record LocationContent(String body, String geoUri, LocationInfo info)
    implements RoomMessage {

  @Override
  public String msgtype() {
    return "m.location";
  }

  /// Additional information of the location data
  ///
  /// @param thumbnailFile information on the encrypted thumbnail file. Currently not supported.
  /// @param thumbnailInfo metadata about the image referred to in `thumbnailUrl`.
  /// @param thumbnailUrl the URL to the thumbnail of the file. Only present if the thumbnail is
  ///   unencrypted.
  public record LocationInfo(
      EncryptedFile thumbnailFile, ThumbnailInfo thumbnailInfo, URI thumbnailUrl)
      implements HasThumbnail {}
}
