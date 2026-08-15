package io.github.hikingc.matrixsdk.api.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.events.server.ephemeral.*;

/// A minimal event with no room, sender, or metadata context — used for account data, presence, and
/// to-device events.
///
/// @param <T> the `m.` type event.
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public sealed interface EphemeralEvent<T extends EphemeralContent> permits DirectEvent, FullyReadEvent, PresenceEvent, ReceiptEvent, TagEvent, TypingEvent {
  /// the event content, shape depends on `type`.
  ///
  /// @return the event content.
  T content();

  /// the event type, for example, `m.typing`.
  ///
  /// @return the event type.
  String type();
}
