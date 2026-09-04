package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;

/// Represents types of reasons for hanging up.
public enum ReasonType {
  /// ICE negotiation has failed and a media connection could not be established.
  @JsonProperty("ice_timeout")
  ICE_TIMEOUT,
  /// the connection failed after some media was exchanged (as opposed to ice_failed which means no
  /// media connection could be established). Note that, in the case of an ICE renegotiation, a
  /// client should be sure to send ice_timeout rather than ice_failed if media had previously been
  /// received successfully, even if the ICE renegotiation itself failed.
  @JsonProperty("ice_failed")
  ICE_FAILED,
  /// the other party did not answer in time.
  @JsonProperty("invite_timeout")
  INVITE_TIMEOUT,
  /// clients must now send this code when the user chooses to end the call, although for backwards
  /// compatibility with version 0, a clients should treat an absence of the reason field as
  /// user_hangup.
  @JsonProperty("user_hangup")
  USER_HANGUP,
  /// the client was unable to start capturing media in such a way that it is unable to continue the
  /// call.
  @JsonProperty("user_media_failed")
  USER_MEDIA_FAILED,
  /// the user is busy. Note that this exists primarily for bridging to other networks such as the
  /// PSTN. A Matrix client that receives a call whilst already in a call would not generally reject
  /// the new call unless the user had specifically chosen to do so.
  @JsonProperty("user_busy")
  USER_BUSY,
  /// some other failure occurred that meant the client was unable to continue the call rather than
  /// the user choosing to end it.
  @JsonProperty("unknown_error")
  UNKNOWN_ERROR
}
