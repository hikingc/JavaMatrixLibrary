package io.github.hikingc.matrixsdk.api.events.matrix.room.messages;

public sealed interface HasArea permits ImageInfo, ThumbnailInfo, VideoContent.VideoInfo {
  Integer h();

  Integer w();

}
