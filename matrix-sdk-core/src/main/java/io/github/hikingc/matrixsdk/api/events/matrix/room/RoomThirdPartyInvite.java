package io.github.hikingc.matrixsdk.api.events.matrix.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.net.URI;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Content information about a third party invite. Acts as an `m.room.member` invite , where there
/// isn’t a target `user_id` to invite.
///
/// @param displayName a user-readable string which represents the user who has been invited. This
///   should not contain the user’s third-party ID, as otherwise when the invite is accepted it
///   would leak the association between the matrix ID and the third-party ID.
/// @param keyValidityUrl a [URI] which can be fetched, with querystring public_key=public_key, to
///   validate whether the key has been revoked. The URL must return a JSON object containing a
///   boolean property named ‘valid’.
/// @param publicKey an `Ed25519` key with which the token must be signed (though a signature from
///   any entry in `public_keys` is also sufficient).
///
///   The key is encoded using Unpadded Base64, using the standard or URL-safe alphabets.
///
///   **This exists for backwards compatibility.**
/// @param publicKeys keys with which the token may be signed.
@NullMarked
public record RoomThirdPartyInvite(
    @JsonProperty(required = true) String displayName,
    @JsonProperty(required = true) URI keyValidityUrl,
    @JsonProperty(required = true) String publicKey,
    List<PublicKeys> publicKeys)
    implements StateEventContent {

  /// Information about the Keys.
  ///
  /// @param keyValidityUrl an optional `URL` which can be fetched, with querystring
  ///   `public_key=<public_key>`, to validate whether the key has been revoked. The URL must return
  ///   a JSON object containing a boolean property named valid. If this URL is absent, the key must
  ///   be considered valid indefinitely.
  /// @param publicKey an Ed25519 key with which the token may be signed.
  ///
  ///   The key is encoded using Unpadded Base64, using the standard or URL-safe alphabets.
  @NullMarked
  public record PublicKeys(
      @Nullable String keyValidityUrl, @JsonProperty(required = true) String publicKey) {}
}
