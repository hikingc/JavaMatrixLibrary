package io.github.hikingc.matrixsdk.api.events.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.ClientEvent;
import io.github.hikingc.matrixsdk.api.events.EphemeralEvent;
import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.EphemeralPresence;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NullMarked;

/// Represents the response body of a `/sync` request against a Matrix homeserver.
///
/// This is the top-level payload returned when polling for account, room, presence, and device
/// updates.
///
/// @param accountData global account data not scoped to a specific room.
/// @param deviceLists devices that changed or left the sync scope, used for end-to-end encryption
///   tracking.
/// @param nextBatch the batch token to supply as `since` on the next sync request.
/// @param presence presence updates for users the client is tracking.
/// @param rooms updates for rooms the client is invited to, joined in, knocking on, or has left.
/// @param toDevice to-device messages sent directly to this client.
public record Sync(
    AccountData accountData,
    DeviceLists deviceLists,
    String nextBatch,
    Presence presence,
    Rooms rooms,
    ToDevice toDevice) {

  /// A batch of account-data events.
  ///
  /// @param events the account data events.
  public record AccountData(List<ClientEvent<?>> events) {}

  /// A batch of presence events.
  ///
  /// @param events the presence events.
  public record Presence(List<EphemeralEvent<EphemeralPresence>> events) {}

  /// Devices whose identity or cross-signing keys changed, relevant for E2EE.
  ///
  /// @param changed device IDs of users whose devices have changed.
  /// @param left device IDs of users no longer sharing an encrypted room with this client.
  public record DeviceLists(List<String> changed, List<String> left) {}

  /// A batch of to-device events.
  ///
  /// @param events the to-device events.
  public record ToDevice(List<Object> events) {}

  /// Room updates grouped by the client's membership state in each room.
  ///
  /// @param invite rooms the client has been invited to, keyed by room ID.
  /// @param join rooms the client is currently joined to, keyed by room ID.
  /// @param knock rooms the client is knocking on, keyed by room ID.
  /// @param leave rooms the client has left or been banned from, keyed by room ID.
  public record Rooms(
      Map<String, InvitedRoom> invite,
      Map<String, JoinedRoom> join,
      Map<String, KnockedRoom> knock,
      Map<String, LeftRoom> leave) {

    /// A room the client has been invited to but not yet joined.
    ///
    /// @param inviteState the stripped state events describing the room prior to joining.
    public record InvitedRoom(InviteState inviteState) {

      /// Stripped state for an invited room.
      ///
      /// @param events the stripped state events.
      public record InviteState(List<StrippedStateEvent> events) {}
    }

    /// A room the client is currently joined to.
    ///
    /// @param accountData account data scoped to this room.
    /// @param ephemeral ephemeral events for this room, e.g. typing notifications.
    /// @param state the full room state.
    /// @param stateAfter the room state after the timeline events in this response are applied.
    /// @param summary a summary of the room, e.g. heroes and member counts.
    /// @param timeline the timeline of events for this room.
    /// @param unreadNotifications unread notification counts for this room.
    /// @param unreadThreadNotifications unread notification counts per thread, keyed by thread root
    ///   event ID.
    public record JoinedRoom(
        AccountData accountData,
        Ephemeral ephemeral,
        State state,
        State stateAfter, // This is required only if the use_state_after is set
        RoomSummary summary,
        Timeline timeline,
        UnreadNotificationCounts unreadNotifications,
        Map<String, ThreadNotificationCounts> unreadThreadNotifications) {

      /// A batch of ephemeral events for a joined room.
      ///
      /// @param events the ephemeral events.
      public record Ephemeral(List<EphemeralEvent<?>> events) {}

      /// Summary information about a room, used to render it without loading full state.
      ///
      /// @param mHeroes a list of user IDs representative of the room's other members.
      /// @param mInvitedMemberCount the number of users with `invite` membership.
      /// @param mJoinedMemberCount the number of users with `join` membership.
      public record RoomSummary(
          @JsonProperty("m.heroes") List<String> mHeroes,
          @JsonProperty("m.invited_member_count") Integer mInvitedMemberCount,
          @JsonProperty("m.joined_member_count") Integer mJoinedMemberCount) {}

      /// Unread notification counts for a room.
      ///
      /// @param highlightCount the number of unread notifications that are highlights.
      /// @param notificationCount the total number of unread notifications.
      public record UnreadNotificationCounts(Integer highlightCount, Integer notificationCount) {}

      /// Unread notification counts scoped to a single thread.
      ///
      /// @param highlightCount the number of unread notifications in this thread that are
      ///   highlights.
      /// @param notificationCount the total number of unread notifications in this thread.
      public record ThreadNotificationCounts(Integer highlightCount, Integer notificationCount) {}
    }

    /// A room the client is knocking on.
    ///
    /// @param knockState the stripped state events describing the room prior to the knock being
    ///   accepted.
    public record KnockedRoom(KnockState knockState) {

      /// Stripped state for a knocked room.
      ///
      /// @param events the stripped state events.
      public record KnockState(List<StrippedStateEvent> events) {}
    }

    /// A room the client has left or been banned from.
    ///
    /// @param accountData account data scoped to this room, as of leaving.
    /// @param state the room state at the point the client stopped receiving updates.
    /// @param stateAfter the room state after the timeline events in this response are applied.
    /// @param timeline the timeline of events leading up to the client leaving the room.
    public record LeftRoom(
        AccountData accountData, State state, State stateAfter, Timeline timeline) {}
  }

  /// A reduced-detail state event included in invite or knock previews, omitting fields such as
  /// `event_id` and timestamps that are not part of the stripped-state contract.
  ///
  /// @param content the event content, shape depends on `type`.
  /// @param sender the user ID of the event's sender.
  /// @param stateKey the state key for this event.
  /// @param type the event type, e.g. `m.room.member`
  @NullMarked
  public record StrippedStateEvent(
      @JsonProperty(required = true) Object content,
      @JsonProperty(required = true) UserID sender,
      @JsonProperty(required = true) String stateKey,
      @JsonProperty(required = true) String type) {}

  /// A batch of room state events.
  ///
  /// @param events the state events.
  public record State(List<ClientEvent<?>> events) {}

  /// A paginated batch of timeline events for a room.
  ///
  /// @apiNote The event list contains a `null` Room ID field
  ///
  /// @param events the timeline events, in chronological order.
  /// @param limited whether the timeline was truncated, requiring further pagination to retrieve
  ///   earlier events.
  /// @param prevBatch a pagination token for retrieving events older than this batch.
  public record Timeline(List<ClientEvent<?>> events, Boolean limited, String prevBatch) {}
}
