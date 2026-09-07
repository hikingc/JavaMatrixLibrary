package io.github.hikingc.matrixsdk.context;

import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;

/// ClientContext stores global context data for internal services.
///
/// @param token The user token.
/// @param domainInformation The discovery response data.
public record ClientContext(String token, DomainInformation domainInformation) {}
