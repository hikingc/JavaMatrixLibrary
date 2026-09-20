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
package io.github.hikingc.matrixsdk.services.filtering;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.filters.FilterDefinition;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.jackson.databind.json.JsonMapper;

@ExtendWith(InstancioExtension.class)
@WireMockTest
class FilterServiceTest {

  private static final JsonMapper mapper = Mapper.getInstance();
  private static final String AUTH_TOKEN = "1234";
  private static final UserID USER_ID = UserID.create("@matrix:example.org");
  private static MatrixClient client;
  private static DomainInformation DISCOVERY_RESPONSE;
  @Given private FilterDefinition filterDefinition;

  @BeforeAll
  static void setUpDiscovery(WireMockRuntimeInfo wireMockRuntimeInfo) {
    DISCOVERY_RESPONSE =
        new DomainInformation(
            new DomainInformation.HomeserverInfo(wireMockRuntimeInfo.getHttpBaseUrl()), null, null);
  }

  @BeforeEach
  void createClient() {
    client =
        new MatrixClientBuilder()
            .setdomainInformation(DISCOVERY_RESPONSE)
            .setAuthToken(AUTH_TOKEN)
            .createMatrixClient();
  }

  @Test
  @DisplayName("Publish a filter and get a server generated ID")
  void publishFilter_WithACorrectPayload_thenReturnAnId() {
    String json = mapper.writeValueAsString(filterDefinition);
    stubFor(
        post("/_matrix/client/v3/user/" + USER_ID + "/filter")
            .withRequestBody(equalToJson(json))
            .willReturn(
                okJson(
                    """
                    {
                      "filter_id": "66696p746572"
                    }\
                    """)));

    String response = client.filter().publishFilter(USER_ID, filterDefinition);

    assertNotNull(response);
  }

  @Test
  @DisplayName("Get a filter definition from a server generated ID")
  void getFilter_WithACorrectPayload_ThenReturnAFilterDefinition() {
    final String FILTER_ID = "ABC123";
    String json = mapper.writeValueAsString(filterDefinition);
    stubFor(
        get("/_matrix/client/v3/user/" + USER_ID + "/filter/" + FILTER_ID)
            .willReturn(okJson(json)));

    FilterDefinition response = client.filter().getFilter(USER_ID, FILTER_ID);

    assertNotNull(response);
  }
}
