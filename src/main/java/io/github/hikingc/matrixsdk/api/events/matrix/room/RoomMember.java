package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.ThirdPartyInvite;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import io.github.hikingc.matrixsdk.api.events.queries.Membership;
import java.net.URI;
import org.jspecify.annotations.Nullable;

public record RoomMember(
    URI avatarUrl,
    @Nullable String displayname,
    Boolean isDirect,
    String joinAuthorizedViaUsersServer,
    Membership membership,
    String reason,
    ThirdPartyInvite thirdPartyInvite)
    implements StateEventContent {}
