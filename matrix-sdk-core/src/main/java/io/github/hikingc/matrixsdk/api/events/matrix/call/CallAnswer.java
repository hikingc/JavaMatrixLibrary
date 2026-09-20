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
import java.util.Map;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// Event content to be sent by the callee when they wish to answer the call.
///
/// @param callId ID of the call this event relates to.
/// @param partyId identifies the party that sent this event. A client may choose to re-use the
/// @param version the version of the VoIP specification this message adheres to. This specification
/// @param answer the session descriptor. device ID from end-to-end cryptography for the value of
///   this field.
/// @param sdp_stream_metadata metadata describing the streams that will be sent.
///
///   This is a map of stream ID to metadata about the stream. is version 1.
@NullMarked
public record CallAnswer(
    @JsonProperty(required = true) String callId,
    @JsonProperty(required = true) String partyId,
    @JsonProperty(required = true) String version,
    @JsonProperty(required = true) Answer answer,
    @Nullable Map<String, StreamMetadata> sdp_stream_metadata)
    implements MessageEventContent, CallEvent {
  /// A session descriptor for [CallAnswer#answer()]
  ///
  /// @param sdp the SDP text of the session description.
  @NullMarked
  public record Answer(@JsonProperty(required = true) String sdp) {

    /// The type of session descriptor
    ///
    /// @return always [CallSessionDescriptorType#ANSWER]
    @JsonProperty("type")
    public CallSessionDescriptorType type() {
      return CallSessionDescriptorType.ANSWER;
    }
  }
}
