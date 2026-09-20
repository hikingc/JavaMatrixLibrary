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
package io.github.hikingc.matrixsdk.api.well_known;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/// Represents discovery information about the domain.
///
/// This includes non-spec keys such as `org.matrix.msc4143.rtc_foci`
///
/// @param homeserver Used to discover homeserver information.
/// @param identityServer Used to discover identity server information.
/// @param rtcFoci Used to store Matrix RTC data that's currently not on spec
@JsonIgnoreProperties(ignoreUnknown = true)
public record DomainInformation(
    @JsonProperty("m.homeserver") HomeserverInfo homeserver,
    @JsonProperty("m.identity_server") IdentityServerInfo identityServer,
    @JsonProperty("org.matrix.msc4143.rtc_foci") List<RtcFocus> rtcFoci) {
  /// Record used to store homeserver information.
  ///
  /// @param baseUrl The base URL for the homeserver for client-server connections.
  public record HomeserverInfo(
      @JsonProperty(required = true) String baseUrl) {} // Err on baseUrl missing

  /// Record used to store identity server information.
  ///
  /// @param baseUrl The base URL for the identity server for client-server connections.
  public record IdentityServerInfo(String baseUrl) {}

  /// Experimental record used to store rtc information.
  ///
  /// @param type The type.
  /// @param livekitServiceUrl The livekit URL.
  public record RtcFocus(String type, String livekitServiceUrl) {}
}

// For future reference about msc2965 field appearing in this response:
// https://github.com/element-hq/synapse/issues/19227
