package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import org.jspecify.annotations.NonNull;

public record EphemeralPresence(
    URI avatarUrl,
    Boolean currentlyActive,
    String displayname,
    Number lastActiveAgo,
    @NonNull @JsonProperty(required = true) PresenceType presence,
    String statusMsg) implements EphemeralContent {}
