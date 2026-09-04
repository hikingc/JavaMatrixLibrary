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
