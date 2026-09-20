/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.api.events.matrix.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.MessageEventContent;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Event content is sent by callers after sending an invitation and by the callee after answering.
/// Its purpose is to give the other party additional ICE candidates to try using to communicate.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
///   device ID from end-to-end cryptography for the value of this field.
/// @param version the version of the VoIP specification this message adheres to. This specification
///   is version 1.
/// @param candidates a list of [Candidate]s
@NullMarked
public record CallCandidates(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @JsonProperty(required = true) List<Candidate> candidates)
    implements MessageEventContent, CallEvent {

  /// The description a candidate.
  ///
  /// @param candidate the SDP ‘a’ line of the candidate.
  ///
  ///   If this is an end-of-candidates candidate, this is the empty string.
  /// @param sdpMLineIndex the index of the SDP 'm' line this candidate is intended for.
  ///
  ///   At least one of `sdpMid` or `sdpMLineIndex` is required, unless this an end-of-candidates
  ///   candidate.
  /// @param sdpMid the SDP media type this candidate is intended for.
  ///
  ///   At least one of `sdpMid` or `sdpMLineIndex` is required, unless this an end-of-candidates
  ///   candidate.
  /// @see <a
  ///   href="https://spec.matrix.org/v1.19/client-server-api/#end-of-candidates">End-of-candidates
  ///   spec.</a>
  public record Candidate(
      @JsonProperty(required = true) String candidate,
      @Nullable Number sdpMLineIndex,
      @Nullable String sdpMid) {}
}
