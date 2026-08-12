package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

import java.util.List;

public record RoomCanonicalAlias(String alias, List<String> altAliases)
    implements StateEventContent {
  public RoomCanonicalAlias {
    altAliases = altAliases == null ? List.of() : List.copyOf(altAliases);
  }
}
