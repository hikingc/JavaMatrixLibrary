package io.github.hikingc.matrixsdk.api.rooms.models;

import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.util.List;

/// This record contains data when resolving a room alias.
///
/// @param roomId the room id for the room alias.
/// @param servers a list of servers aware of said alias.
public record ResolvedAlias(RoomID roomId, List<String> servers) {}
