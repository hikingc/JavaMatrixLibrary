package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.ThirdPartyInvite;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.events.queries.Membership;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/// Content information about a user in a room and their membership status. It is recommended to use
/// [Room][io.github.hikingc.matrixsdk.api.Room] to change a users' membership status.
///
/// @param avatarUrl the avatar [URI] for this user, if any.
/// @param displayname the display name for this user, if any.
/// @param isDirect flag indicating if the room containing this event was created with the intention
///   of being a direct chat. See [Direct
///   Messaging](https://spec.matrix.org/v1.19/client-server-api/#direct-messaging).
/// @param joinAuthorizedViaUsersServer usually found on `join` events, this field is used to denote
///   which homeserver (through representation of a user with sufficient power level) authorized the
///   user’s join. More information about this field can be found in the [Restricted Rooms
///   Specification](https://spec.matrix.org/v1.19/client-server-api/#restricted-rooms).
///
///   **Client and server implementations should be aware of the [signing
///   implications](https://spec.matrix.org/v1.19/rooms/v8/#authorisation-rules) of including this
///   field in further events: in particular, the event must be signed by the server which owns the
///   user ID in the field. When copying the membership event’s content (for profile updates and
///   similar) it is therefore encouraged to exclude this field in the copy, as otherwise the event
///   might fail event authorization.**
/// @param membership the membership state of the user.
/// @param reason optional user-supplied text for why their membership has changed. For kicks and
///   bans, this is typically the reason for the kick or ban. For other membership changes, this is
///   a way for the user to communicate their intent without having to send a message to the room,
///   such as in a case where Bob rejects an invitation from Alice about an upcoming concert, but
///   can’t make it that day.
///
///   **Clients are not recommended to show this reason to users when receiving an invitation due to
///   the potential for spam and abuse. Hiding the reason behind a button or other component is
///   recommended.**
/// @param thirdPartyInvite a third-party invite, if this `m.room.member` is the successor to an
///   [`m.room.third_party_invite`][io.github.hikingc.matrixsdk.api.events.server.state.RoomThirdPartyInviteEvent]
///   event.
public record RoomMember(
    URI avatarUrl,
    @Nullable String displayname,
    Boolean isDirect,
    String joinAuthorizedViaUsersServer,
    Membership membership,
    String reason,
    ThirdPartyInvite thirdPartyInvite)
    implements StateEventContent {}
