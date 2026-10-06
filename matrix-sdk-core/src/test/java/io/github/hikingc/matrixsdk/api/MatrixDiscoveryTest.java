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
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WireMockTest
class MatrixDiscoveryTest {
  private static MatrixDiscovery matrixDiscovery;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wireMockRuntimeInfo) {
    matrixDiscovery = new MatrixDiscovery(URI.create(wireMockRuntimeInfo.getHttpBaseUrl()), null);
  }

  @Test
  @DisplayName("Get client's well_known response")
  void getFetchWellKnown_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "m.homeserver": {
                            "base_url": "https://matrix.example.com"
                          },
                          "m.identity_server": {
                            "base_url": "https://identity.example.com"
                          },
                          "org.example.custom.property": {
                            "app_url": "https://custom.app.example.org"
                          }
                        }

                        """)));
    var response = matrixDiscovery.fetchWellKnown();
    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("Get support's well_known response")
  void getFetchSupportServer_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/support"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "contacts": [
                            {
                              "email_address": "admin@example.org",
                              "matrix_id": "@admin:example.org",
                              "role": "m.role.admin"
                            },
                            {
                              "email_address": "security@example.org",
                              "role": "m.role.security"
                            }
                          ],
                          "support_page": "https://example.org/support.html"
                        }


                        """)));
    var response = matrixDiscovery.fetchServerSupport();
    assertThat(response).isNotNull();
    assertThat(response.supportPage()).isEqualTo(URI.create("https://example.org/support.html"));
  }

  @Test
  @DisplayName("Get policy's well_known response")
  void getFetchPolicyServer_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/policy_server"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "public_keys": {
                            "ed25519": "6yhHGKhCiXTSEN2ksjV7kX_N6rBQZ3Xb-M7LlC6NS-s"
                          }
                        }

                        """)));
    var response = matrixDiscovery.fetchPolicyServer();
    assertThat(response).isNotNull();
    assertThat(response.publicKeys().get("ed25519"))
        .contains("6yhHGKhCiXTSEN2ksjV7kX_N6rBQZ3Xb-M7LlC6NS-s");
  }
}
