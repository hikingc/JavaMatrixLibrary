package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Possible values of the stream purpose.
public enum PurposeType {
  /// Stream that contains the webcam and/or microphone tracks.
  @JsonProperty("m.usermedia")
  WEBCAM_AND_OR_MICROPHONE,
  /// Stream with the screen-sharing tracks.
  @JsonProperty("m.screenshare")
  SCREEN_SHARE
}
