package io.github.hikingc.matrixsdk.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

/// Contains retrieval data from `legacy` /login API.
/// This record does **NOT** implement all keys that the server might return.
///
/// Use [the well-known service][io.github.hikingc.matrixsdk.api.MatrixDiscovery] for Discovery Information.
///
/// @param accessToken  an access token for the account. This access token can then be used to authorize other requests.
/// @param deviceId     ID of the logged-in device. Will be the same as the corresponding parameter in the request, if one was specified.
/// @param expiresInMs  the lifetime of the access token, in milliseconds. Once the access token has expired a new access token can be obtained by using the provided refresh token. If no refresh token is provided, the client will need to re-log in to obtain a new access token. If not given, the client can assume that the access token will not expire.
/// @param refreshToken the server_name of the homeserver on which the account has been registered.
/// @param userId       the fully-qualified Matrix ID for the account.
public record LegacyMetadata(@JsonProperty(required = true) @NonNull String accessToken,
                             @JsonProperty(required = true) @NonNull String deviceId,
                             String expiresInMs,
                             String refreshToken,
                             @JsonProperty(required = true) @NonNull String userId) {

}
