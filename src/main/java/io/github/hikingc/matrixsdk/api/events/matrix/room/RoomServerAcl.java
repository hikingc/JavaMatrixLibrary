package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;

/// Content information about server access control lists.
///
/// @implNote It is **recommended** to read in-detail about this event content.
///
/// @param allow the case-insensitive glob expressions that are evaluated against server names
///   excluding any port information to determine the servers to allow in the room.
///
///   **This defaults to an empty list when not provided, effectively disallowing every server.**
/// @param allowIpLiterals true to allow server names that are IP address literals. False to deny.
///   Defaults to true if missing or otherwise not a boolean.
///
///   This is strongly recommended to be set to false as servers running with IP literal names are
///   strongly discouraged in order to require legitimate homeservers to be backed by a valid
///   registered domain name.
/// @param deny the case-insensitive glob expressions that are evaluated against server names
///   excluding any port information to determine the servers to disallow in the room.
///
///   This defaults to an empty list when not provided.
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#mroomserver_acl">`m.room.server_acl` in
///   the spec.</a>
public record RoomServerAcl(List<String> allow, Boolean allowIpLiterals, List<String> deny)
    implements StateEventContent {}
