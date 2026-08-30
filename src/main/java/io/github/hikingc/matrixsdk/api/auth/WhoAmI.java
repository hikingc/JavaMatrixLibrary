package io.github.hikingc.matrixsdk.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import org.jspecify.annotations.NonNull;

/// Holds `/whoami` information from the server.
///
/// @param deviceId the Device ID associated with the access token.
///
///   If no device is associated with the access token (such as in the case of application services)
///   then this field can be omitted. Otherwise, this is required.
/// @param isGuest when `true`, the user is a Guest User. When not present or `false`, the user is
///   presumed to be a non-guest user.
/// @param userId the [UserID] that owns the access token.
public record WhoAmI(
    String deviceId, Boolean isGuest, @NonNull @JsonProperty(required = true) UserID userId) {}
