package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;

/// Event content that informs the room about what is the canonical alias to be used. This could be
/// for display purposes or as suggestion to users which alias to use to advertise and access the
/// room.
///
/// @param alias the canonical alias for the room. If not present, null, or empty the room should be
///   considered to have no canonical alias.
/// @param altAliases alternative aliases the room advertises. This list can have aliases despite
///   the alias field being null, empty, or otherwise not present.
public record RoomCanonicalAlias(String alias, List<String> altAliases)
    implements StateEventContent {
  public RoomCanonicalAlias {
    altAliases = altAliases == null ? List.of() : List.copyOf(altAliases);
  }
}
