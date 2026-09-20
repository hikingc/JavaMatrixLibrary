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
package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

/// Holds stream metadata
///
/// @param audioMuted whether the audio track in the stream is muted.
///
///   Defaults to false if not present.
/// @param purpose of the stream.
/// @param videoMuted Whether the video track in the stream is muted.
///
///   Defaults to false if not present.
public record StreamMetadata(
    Boolean audioMuted,
    @NonNull @JsonProperty(required = true) PurposeType purpose,
    Boolean videoMuted) {
  /// Normalizes null mute-state fields to their default (unmuted) values.
  public StreamMetadata {
    if (audioMuted == null) {
      audioMuted = false;
    }
    if (videoMuted == null) {
      videoMuted = false;
    }
  }
}
