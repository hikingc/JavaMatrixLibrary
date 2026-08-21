package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import java.util.Map;

/// Content information of tags on a room.
///
/// @param tags the tags on the room and their contents.
public record EphemeralTag(Map<String, Tag> tags) implements EphemeralContent {

  /// @param order a number in a range `[0,1]` describing a relative position of the room under the given tag.
  public record Tag(Float order) {}
}
