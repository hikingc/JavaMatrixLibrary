package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.net.URI;
import java.util.List;
import org.jspecify.annotations.NonNull;

public record ThirdPartyInvite(
    @NonNull @JsonProperty(required = true) String displayName,
    @NonNull @JsonProperty(required = true) URI keyValidityUrl,
    @NonNull @JsonProperty(required = true) String publicKey,
    List<PublicKeys> publicKeys)
    implements StateEventContent {

  public record PublicKeys(
      String keyValidityUrl, @NonNull @JsonProperty(required = true) String publicKey) {}
}
