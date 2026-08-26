package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.Room;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.rooms.InitialRoomConfiguration;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Content information about what is currently being discussed in the room. It can also be used as
/// a way to display extra information about the room, which may not be suitable for the room name.
///
/// If the `topic` property is absent, null, or empty then the `topic` is unset. In other words, an
/// empty topic property effectively resets the room to having no topic.
///
/// In order to prevent formatting abuse in room topics, clients **SHOULD** limit the length of
/// topics during both entry and display, for instance, by capping the number of displayed lines.
/// Additionally, clients **SHOULD** ignore things like headings and enumerations (or format them as
/// regular text).
///
/// @param mTopic textual representation of the room topic in different mimetypes.
/// @param topic the topic in plain text (**SHOULD** be a duplicate of mTopic if set).
///
/// @see Room#create(InitialRoomConfiguration)
public record RoomTopic(
    @JsonProperty("m.topic") TopicContentBlock mTopic,
    @NonNull @JsonProperty(required = true) String topic)
    implements StateEventContent {
  /// Object information about the content topic.
  ///
  /// @param mText An ordered array of textual representations in different mimetypes.
  ///
  ///   Senders **SHOULD** specify at least one representation and SHOULD always include a plaintext
  ///   representation.
  ///
  ///   Receivers **SHOULD** use the first representation in the array that they understand.
  public record TopicContentBlock(@JsonProperty("m.text") List<TextualRepresentation> mText) {

    /// Object information about the textual representation.
    ///
    /// @param body the [String] content. **SHOULD** be validated like for [RoomMessage] content
    ///   types.
    /// @param mimetype the mimetype. Defaults to `text/plain` if omitted.
    public record TextualRepresentation(String body, String mimetype) {}
  }
}
