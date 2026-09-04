package io.github.hikingc.matrixsdk.api.events.matrix.room.message;

public sealed interface HasArea permits ImageInfo, ThumbnailInfo, VideoContent.VideoInfo {
  Integer h();

  Integer w();

}
