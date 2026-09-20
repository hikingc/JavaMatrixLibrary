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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
@WireMockTest
class MatrixAPIClientTest {
  private static final String AUTH_TOKEN = "1234";
  @Given private DomainInformation domainInformation;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wireMockRuntimeInfo) {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        "{\"m.homeserver\": {\"base_url\": \""
                            + wireMockRuntimeInfo.getHttpBaseUrl()
                            + "\"}}")));
  }

  @Test
  @DisplayName("Initialize client correctly")
  void getWellKnown_WithAllRequiredProperties_thenReturnCorrectSerialization(
      WireMockRuntimeInfo wireMockRuntimeInfo) {
    MatrixClient client =
        new MatrixClientBuilder()
            .setdomainInformation(domainInformation)
            .setAuthToken(AUTH_TOKEN)
            .createMatrixClient();
    assertDoesNotThrow(() -> client, "The client should not throw given a good url.");
  }
}
