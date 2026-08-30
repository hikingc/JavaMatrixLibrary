package io.github.hikingc.matrixsdk.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NonNull;

/// Represents the versions and unstable features supported by a server.
///
/// @param unstableFeatures experimental features the server supports. Features not listed here, or
///   the lack of this property all together, indicate that a feature is not supported.
/// @param versions the supported versions.
public record Versions(
    Map<String, Boolean> unstableFeatures,
    @NonNull @JsonProperty(required = true) List<String> versions) {}
