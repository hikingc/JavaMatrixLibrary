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
package io.github.hikingc.matrixsdk.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import java.net.http.HttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class MatrixLegacyAuthTest {

  private static MatrixLegacyAuth matrixAuth;
  private static String baseUrl;
  private static DomainInformation DISCOVERY_RESPONSE;
  private final TokenMetadata tokens = new TokenMetadata("ABCD", null, null, null, null);
  private int callbackPort;

  @BeforeAll
  static void setUpDiscovery(WireMockRuntimeInfo wireMockRuntimeInfo) {
    DISCOVERY_RESPONSE =
        new DomainInformation(
            new DomainInformation.HomeserverInfo(wireMockRuntimeInfo.getHttpBaseUrl()), null, null);
  }

  @BeforeEach
  void setupAuth(WireMockRuntimeInfo wireMockRuntimeInfo) {
    stubFor(
        get(urlEqualTo("/_matrix/client/versions"))
            .willReturn(
                okJson(
                    """
                    {
                      "unstable_features": {
                        "org.example.my_feature": true
                      },
                      "versions": [
                        "r0.0.1",
                        "v1.1"
                      ]
                    }
                    """)));
    baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .willReturn(
                okJson(
                    """
                    {"m.homeserver": {"base_url": "%s"}}
                    """
                        .formatted(baseUrl))));
    matrixAuth = new MatrixLegacyAuth(HttpClient.newBuilder().build(), DISCOVERY_RESPONSE);

    stubFor(
        get(urlEqualTo("/_matrix/client/v1/auth_metadata"))
            .willReturn(
                okJson(
                    """
                    {
                      "issuer": "%1$s/",
                      "authorization_endpoint": "%1$s/oauth2/auth",
                      "token_endpoint": "%1$s/oauth2/token",
                      "registration_endpoint": "%1$s/oauth2/clients/register",
                      "revocation_endpoint": "%1$s/oauth2/revoke",
                      "grant_types_supported": ["authorization_code", "refresh_token"],
                      "response_types_supported": ["code"],
                      "response_modes_supported": ["query", "fragment"],
                      "code_challenge_methods_supported": ["S256"]
                    }
                    """
                        .formatted(baseUrl))));
  }

  @Test
  void performAuthLogin() {
    stubFor(
        post(urlEqualTo("/_matrix/client/v3/login"))
            .withRequestBody(
                equalToJson(
                    """
                            {
                              "type": "m.login.password",
                              "identifier": {
                                "type": "m.id.user",
                                "user": "@user:example.org"
                              },
                              "password": "PASSWORD"
                            }
                    """))
            .willReturn(
                okJson(
                    """
                    {
                      "access_token": "abc123",
                      "device_id": "GHTYAJCE",
                      "expires_in_ms": 60000,
                      "refresh_token": "def456",
                      "user_id": "@user:example.org",
                      "well_known": {
                        "m.homeserver": {
                          "base_url": "https://example.org"
                        },
                        "m.identity_server": {
                          "base_url": "https://id.example.org"
                        }
                      }
                    }

                    """)));
    var response = matrixAuth.performAuthLogin(UserID.create("@user:example.org"), "PASSWORD");
    assertThat(response).isNotNull();
  }
}
