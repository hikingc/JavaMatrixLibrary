package io.github.hikingc.matrixsdk.api.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/// This class allows for the representation and validation of an Event ID in Matrix.
///
/// Their form is as follows: `$opaque_id`, some room versions include a `domain` component, whereas
/// more recent room versions omit the domain and use a base64-encoded hash instead.
///
/// The length of a [EventID], including the `$` sigil, **MUST NOT** exceed 255 bytes.
///
/// @see <a href="https://spec.matrix.org/v1.19/appendices/#event-ids">Event Identifiers as defined
///   in the specification</a>
public final class EventID implements Validator {
  private final String opaqueId;

  private EventID(String opaqueId) {
    this.opaqueId = opaqueId;
  }

  /// Builds and validates a [RoomID]
  ///
  /// @param rawRoomId the [String] to validate.
  /// @return a [RoomID].
  /// @throws IllegalArgumentException if the [String] has broken a rule from the spec.
  /// @throws NullPointerException if the [String] is null.
  @JsonCreator
  public static EventID parse(String rawRoomId) {
    Objects.requireNonNull(rawRoomId, "Room ID" + " must not be null");

    if (rawRoomId.getBytes(StandardCharsets.UTF_8).length > MAX_BYTES) {
      throw new IllegalArgumentException("Event ID exceeds " + MAX_BYTES + " bytes");
    }

    if (rawRoomId.isEmpty()) {
      throw new IllegalArgumentException("Event ID must not be empty");
    }

    if (rawRoomId.charAt(0) != '$') {
      throw new IllegalArgumentException("Event ID must start with '$'");
    }

    if (rawRoomId.contentEquals("$")) {
      throw new IllegalArgumentException("Event ID must not only contain '$'");
    }

    return new EventID(rawRoomId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(opaqueId);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (EventID) obj;
    return Objects.equals(this.opaqueId, that.opaqueId);
  }

  @Override
  @JsonValue
  public String toString() {
    return opaqueId;
  }
}
