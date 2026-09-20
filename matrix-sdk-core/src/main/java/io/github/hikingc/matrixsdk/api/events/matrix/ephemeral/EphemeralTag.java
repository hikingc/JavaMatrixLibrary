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
package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import java.util.Map;

/// Content information of tags on a room.
///
/// @param tags the tags on the room and their contents.
public record EphemeralTag(Map<String, Tag> tags) implements EphemeralContent {
  /// Information about the tag
  ///
  /// @param order a number in a range `[0,1]` describing a relative position of the room under the
  ///   given tag.
  public record Tag(Float order) {}
}
