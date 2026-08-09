package io.github.hikingc.matrixsdk.api.rooms;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import org.jspecify.annotations.NullMarked;

/// Holds information to supply the server and verify a `m.room.third_party_invite` event.
///
/// @param mxid the Matrix ID of the invitee.
/// @param sender the Matrix ID of the user who issued the invite.
/// @param signatures a signatures object containing a signature of the entire signed object.
/// @param token the state key of the `m.third_party_invite` event.
@NullMarked
public record ThirdPartySigned(
    @JsonProperty(required = true) UserID mxid,
    @JsonProperty(required = true) UserID sender,
    @JsonProperty(required = true) Map<String, Map<String, String>> signatures,
    @JsonProperty(required = true) String token) {}
