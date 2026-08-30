package io.github.hikingc.matrixsdk.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import java.net.URI;
import java.net.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class MatrixAuthTest {

  private static MatrixAuth matrixAuth;
  private TokenMetadata tokens = new TokenMetadata("ABCD", null, null, null, null);

  @BeforeEach
  void setupAuth(WireMockRuntimeInfo wireMockRuntimeInfo) {
    matrixAuth =
        new MatrixAuth(
            URI.create(wireMockRuntimeInfo.getHttpBaseUrl()), HttpClient.newBuilder().build());
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
  void getCurrentAccountInformation_WithACorrectPayload_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/_matrix/client/v3/account/whoami"))
            .withHeader("Authorization", matching("Bearer .*"))
            .willReturn(
                okJson(
                    """
        {
          "device_id": "ABC1234",
          "user_id": "@joe:example.org"
        }
        """)));
    var response = matrixAuth.getCurrentAccountInformation("token");
    assertThat(response).isNotNull();
  }

  @Test
  void getFetchWellKnown() {
    stubFor(
        get(urlEqualTo("/.well-known/matrix/client "))
            .withHeader("Authorization", containing("Bearer .*"))
            .willReturn(
                okJson(
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
    var response = matrixAuth.fetchWellKnown();
    assertThat(response).isNotNull();
  }

  @Test
  void getVersions_WithACorrectPayload_ReturnAnObject() {
    stubFor(
        get(urlEqualTo("/_matrix/client/versions"))
            .withHeader("Authorization", matching("Bearer .*"))
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
    assertThat(tokens.accessToken()).isNotNull();
    var response = matrixAuth.getVersions(tokens.accessToken());
    assertThat(response).isNotNull();
  }
}
