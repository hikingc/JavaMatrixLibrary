package io.github.hikingc.matrixsdk.api.events.content.roommessages;

public sealed interface HasArea permits ImageInfo, ThumbnailInfo, VideoContent.VideoInfo {
  Integer h();

  Integer w();

}
