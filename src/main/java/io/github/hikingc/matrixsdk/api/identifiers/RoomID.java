package io.github.hikingc.matrixsdk.api.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/// This class allows for the representation and validation of a Room Identifier in Matrix.
///
/// Their form is as follows: `!opaque_id`, some room versions include a `domain` component, whereas
/// more recent room versions omit the domain and use a base64-encoded hash instead.
///
/// The `opaque_id` of a [RoomID] **MUST** only contain valid non-surrogate Unicode code points,
/// including control characters, except `:` and `NUL` (`U+0000`). The localpart **SHOULD** only
/// consist of alphanumeric characters (`A-Z, a-z, 0-9`) when generating them..
///
/// The `domain` of a [RoomID] is the server name of the homeserver which allocated the room.
///
/// The length of a [RoomID], including the `!` sigil and the domain, **MUST NOT** exceed 255 bytes.
///
/// @see <a href="https://spec.matrix.org/v1.19/appendices/#room-ids">Room Identifiers as defined in
///   the specification</a>
public final class RoomID implements Identifier {
  private final String opaqueId;
  private final String domain;

  private RoomID(String opaqueId, String domain) {
    this.opaqueId = opaqueId;
    this.domain = domain;
  }

  /// Builds and validates a [RoomID]
  ///
  /// @param rawRoomId the [String] to validate.
  /// @return a [RoomID].
  /// @throws IllegalArgumentException if the [String] has broken a rule from the spec.
  /// @throws NullPointerException if the [String] is null.
  public static RoomID create(String rawRoomId) {
    Objects.requireNonNull(rawRoomId, "Room ID must not be null");
    return of(rawRoomId, false);
  }

  @JsonCreator
  private static RoomID receive(String value) {
    return of(value, false);
  }

  private static RoomID of(String value, boolean strict) {
    Objects.requireNonNull(value, "Room ID must not be null");
    Validator.validateSigilId(value, '!', "Room ID", strict, true);
    int colonIdx = value.indexOf(':');
    if (colonIdx == -1) {
      return new RoomID(value.substring(1), null);
    }
    return new RoomID(value.substring(1, colonIdx), value.substring(colonIdx + 1));
  }

  @Override
  public int hashCode() {
    return Objects.hash(opaqueId, domain);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (RoomID) obj;
    return Objects.equals(this.opaqueId, that.opaqueId) && Objects.equals(this.domain, that.domain);
  }

  @Override
  @JsonValue
  public String toString() {
    return domain == null ? "!" + opaqueId : "!" + opaqueId + ":" + domain;
  }
}
