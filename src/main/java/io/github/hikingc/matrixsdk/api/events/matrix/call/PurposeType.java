package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PurposeType {
  @JsonProperty("m.usermedia")
  WEBCAM_AND_OR_MICROPHONE,
  @JsonProperty("m.screenshare")
  SCREEN_SHARE
}
