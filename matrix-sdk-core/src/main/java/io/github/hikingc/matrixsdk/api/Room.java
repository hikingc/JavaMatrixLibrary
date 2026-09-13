package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.identifiers.Identifier;
import io.github.hikingc.matrixsdk.api.identifiers.RoomAlias;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.rooms.InitialRoomConfiguration;
import io.github.hikingc.matrixsdk.api.rooms.PublicRoomDirectory;
import io.github.hikingc.matrixsdk.api.rooms.PublicRoomRequest;
import io.github.hikingc.matrixsdk.api.rooms.RoomMembershipRequest;
import io.github.hikingc.matrixsdk.api.rooms.models.ResolvedAlias;
import io.github.hikingc.matrixsdk.api.rooms.models.RoomSummary;
import io.github.hikingc.matrixsdk.api.rooms.queries.JoinRoomRequest;
import io.github.hikingc.matrixsdk.api.rooms.queries.VisibilityRoomType;
import io.github.hikingc.matrixsdk.exceptions.MatrixApiException;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;


import java.util.List;

/// Core interface for executing protocol operations against Rooms.
///
/// All operations in this interface are blocking. Implementations must ensure thread safety and
/// avoid synchronization blocks that cause carrier thread pinning during network I/O.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#rooms">Matrix Client-Server API
///   Specification for Rooms</a>
public interface Room {
    /// Creates a room based on the received [InitialRoomConfiguration].
    ///
    /// @param configuration of the room.
    /// @return the created room’s ID.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    String create(InitialRoomConfiguration configuration);

    /// Requests the server to resolve a room alias if not possible, the server will use the
    /// federation API.
    ///
    /// @param roomAlias the room alias.
    /// @return a [ResolvedAlias] containing the room ids for the requested alias and which servers
    ///   are aware of it.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    ResolvedAlias resolveAlias(RoomAlias roomAlias);

    /// Sets a room alias to a room.
    ///
    /// @param roomAlias a [RoomAlias].
    /// @param roomId    the [RoomID] to receive the alias.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void setAlias(RoomAlias roomAlias, RoomID roomId);

    /// Requests the server to remove a mapping of a room alias to a room id. On success, servers
    /// might modify `m.room.canonical_alias`
    ///
    /// @param roomAlias the [RoomAlias] to remove.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void deleteAlias(RoomAlias roomAlias);

    /// Requests a list of aliases maintained by the local server for the given room, requires to be
    /// part of the room unless it is configured to be world readable.
    ///
    /// Spec Note: Don't use this endpoint to display data as it is not curated, use data from
    /// `m.room.canonical_alias`.
    ///
    /// @param roomId the [RoomID] to find local aliases of.
    /// @return a [List] of Room aliases.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    List<String> getAliasesOfARoom(RoomID roomId);

    /// Requests the server to retrieve a list of the user's current rooms (in simple terms whoever
    /// calls this method).
    ///
    /// @return a [List] of the rooms.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    List<String> getJoinedRooms();

    /// Send an invitation to a user to participate in a room, this endpoint requires the caller to be
    /// a member of said room to invite other users.
    ///
    /// @param roomId the target [RoomID].
    /// @param event  a [RoomMembershipRequest] with the appropriate information.
    /// @see <a
    ///   href="https://spec.matrix.org/v1.18/client-server-api/#third-party-invites">third-party
    ///   invites spec</a> for another type of invitation.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void inviteUser(RoomID roomId, RoomMembershipRequest event);

    /// If allowed, it starts participation in a room.
    ///
    /// @param roomIdOrAlias a [RoomID] or [RoomAlias] to be targeted.
    /// @param request       a [JoinRoomRequest] where additional information can be passed.
    /// @param via           the servers to attempt to join the room through. One of the servers must be
    ///   participating in the room.
    /// @return the room ID.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    /// @throws IllegalArgumentException   when using an incorrect [Validator][io.github.hikingc.matrixsdk.api.identifiers.Validator].
    String joinByRoomIdOrAliasIfAllowed(
            Identifier roomIdOrAlias, JoinRoomRequest request, List<String> via);

    /// If allowed, it starts participation in a room.
    ///
    /// @param roomId  the target [RoomID].
    /// @param request a [JoinRoomRequest] where additional information can be passed.
    /// @param via     the servers to attempt to join the room through. One of the servers must be
    ///   participating in the room.
    /// @return the room ID.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    String joinByRoomIdIfAllowed(RoomID roomId, JoinRoomRequest request, List<String> via);

    /// Knock on a room to ask for permission to join. Acceptance of this request happens out of band.
    ///
    /// @param roomIdOrAlias a [RoomID] or [RoomAlias] to be targeted.
    /// @param reason        an optional reason to include in the event.
    /// @param via           the servers to attempt to join the room through. One of the servers must be
    ///   participating in the room.
    /// @return the room ID of the knocked room.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    String knockOn(Identifier roomIdOrAlias, String reason, List<String> via);

    /// Sends a request to leave the room, upon success, you will forget all messages from this room.
    /// If all users on a room forget it, the room is eligible for deletion. You must
    /// [Room#forget(RoomID)] the room first before calling this method.
    ///
    /// @param roomId the target [RoomID].
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void forget(RoomID roomId);

    /// Sends a request to leave the room, upon success, you will no longer receive new messages from
    /// this room. If the user was invited to the room, but had not joined, this call serves to reject
    /// the invite. Some servers MAY additionally `forget` the room when leaving.
    ///
    /// @param roomId the target [RoomID].
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void leave(RoomID roomId);

    /// Sends a request to kick someone from a room. Caller must have a configured power level to
    /// perform this operation.
    ///
    /// @param roomId the target [RoomID].
    /// @param event  the body to supply the request.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void kick(RoomID roomId, RoomMembershipRequest event);

    /// Sends a request to ban someone from a room. Caller must have a configured power level to
    /// perform this operation.
    ///
    /// @param roomId the target [RoomID].
    /// @param event  the body to supply the request.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void ban(RoomID roomId, RoomMembershipRequest event);

    /// Sends a request to unban someone from a room. Caller must have a configured power level to
    /// perform this operation.
    ///
    /// @param roomId the target [RoomID].
    /// @param event  the body to supply the request.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void unban(RoomID roomId, RoomMembershipRequest event);

    /// Gets the visibility of a given room in the server’s published room directory. Authentication
    /// is not required to run this request. NOTE: This does NOT guarantee join rules are public.
    ///
    /// @param roomId the target [RoomID].
    /// @return a [String] with the room visibility.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    String getRoomDirectoryVisibilityType(RoomID roomId);

    /// Sets the visibility of a given room in the server’s published room directory.
    ///
    /// @param roomId   the target [RoomID].
    /// @param roomType a [VisibilityRoomType] with the room visibility type
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    void setRoomDirectoryVisibilityType(RoomID roomId, VisibilityRoomType roomType);

    /// Lists a server’s published room directory.
    ///
    /// @param limit  of records to show
    /// @param server to fetch from, if not supplied it will fetch the local server. Case-sensitive.
    /// @param since  a pagination token from a previous request, allowing you to get the next or
    ///   previous batch of rooms. The direction of pagination is specified by which token is
    ///   supplied.
    /// @return a [PublicRoomDirectory] containing records of the published rooms on the server.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    /// @see #getPublishedRoomDirectory(PublicRoomRequest)
    ///   getPublishedRoomDirectory(PublicRoomRequest) for a filterable response.
    PublicRoomDirectory getPublishedRoomDirectory(Integer limit, String server, String since);

    /// Lists a server’s published room directory.
    ///
    /// @param request a [PublicRoomRequest] with additional filters for the request.
    /// @return a [PublicRoomDirectory] containing records of the published rooms on the server.
    /// @throws MatrixException    if an error occurred while processing.
    /// @throws MatrixApiException if the server returns an unsuccessful answer.
    PublicRoomDirectory getPublishedRoomDirectory(PublicRoomRequest request);

    /// Retrieves a summary for a room. The response data might yield outdated, partial or even with
    /// no data.
    ///
    /// @param roomIdOrAlias a [RoomID] or [RoomAlias] of the room to target.
    /// @param via           the servers to attempt to request the summary from when the local server cannot
    ///   generate it.
    /// @return a [RoomSummary] containing all the information about the room.
    /// @throws NullPointerException when the roomId is null.
    /// @throws MatrixException      if an error occurred while processing.
    /// @throws MatrixApiException   if the server returns an unsuccessful answer.
    RoomSummary getRoomSummary(Identifier roomIdOrAlias, List<String> via);
}
