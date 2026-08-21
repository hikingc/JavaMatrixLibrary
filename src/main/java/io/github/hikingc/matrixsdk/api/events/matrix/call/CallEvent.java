package io.github.hikingc.matrixsdk.api.events.matrix.call;

/// Interface that models common **required** fields on Call events.
///
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#common-fields">Common fields
///   according to the specification.</a>
public sealed interface CallEvent
    permits CallAnswer,
        CallCandidates,
        CallHangup,
        CallInvite,
        CallNegotiate,
        CallReject,
        CallSelectAnswer {
  /// The ID of the call this event relates to.
  ///
  /// @return a call ID.
  String callId();

  /// This identifies the party that sent this event. A client may choose to re-use the device ID
  /// from end-to-end cryptography for the value of this field.
  ///
  /// @return a party ID.
  String partyId();

  /// The version of the VoIP specification this message adheres to. This specification is version
  /// 1. This field is a string such that experimental implementations can use noninteger versions.
  ///
  /// @return a String with the version.
  String version();
}
