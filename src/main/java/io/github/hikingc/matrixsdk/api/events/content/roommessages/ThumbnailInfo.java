package io.github.hikingc.matrixsdk.api.events.content.roommessages;

import io.github.hikingc.matrixsdk.api.events.content.RoomMessage;

/// Holds information used by [RoomMessage] events to represent metadata information
///
/// @param h the intended display height of the image in pixels. This may differ from the intrinsic
///   dimensions of the image file.
/// @param w the intended display width of the image in pixels. This may differ from the intrinsic
///   dimensions of the image file.
/// @param size the size of the image in bytes.
/// @param mimetype the mimetype of the image.
public record ThumbnailInfo(Integer h, Integer w, Integer size, String mimetype)
    implements HasArea, HasInfo {}
