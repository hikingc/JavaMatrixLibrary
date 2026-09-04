package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.List;

/// Content information about the list of users currently typing.
///
/// @param userIds the list of user IDs typing in this room, if any.
public record EphemeralTyping(List<UserID> userIds) implements EphemeralContent {}
