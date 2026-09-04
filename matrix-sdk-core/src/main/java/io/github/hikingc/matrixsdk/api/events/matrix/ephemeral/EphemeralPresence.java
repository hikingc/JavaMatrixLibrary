package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import java.net.URI;
import org.jspecify.annotations.NonNull;

/// Content information of when the user’s client presence state change.
///
/// @param avatarUrl the current avatar URL for this user, if any.
/// @param currentlyActive whether the user is currently active
/// @param displayname the current display name for this user, if any.
/// @param lastActiveAgo the last time since this used performed some action, in milliseconds.
/// @param presence the presence state for this user.
/// @param statusMsg an optional description to accompany the presence.
public record EphemeralPresence(
    URI avatarUrl,
    Boolean currentlyActive,
    String displayname,
    Number lastActiveAgo,
    @NonNull @JsonProperty(required = true) PresenceType presence,
    String statusMsg)
    implements EphemeralContent {}
