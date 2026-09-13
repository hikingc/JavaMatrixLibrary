package io.github.hikingc.matrixsdk.services.userdata;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.userdata.UserProfile;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WireMockTest
class UserDataServiceTest {

  private static final String AUTH_TOKEN = "1234";
  private static final UserID USER_ID = UserID.create("@user:example.com");
  private static MatrixClient client;
  private static DomainInformation DISCOVERY_RESPONSE;

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
  @DisplayName("Find users with a search term and return them")
  void searchUsersByTerm() {
    stubFor(
        post(urlEqualTo("/_matrix/client/v3/user_directory/search"))
            .willReturn(
                okJson(
                    """
                    {
                      "results": [
                        {"user_id": "@user:example.com", "display_name": "foo"}
                      ],
                      "limited": false
                    }
                    """)));

    var results = client.userData().searchUsersByTerm(10, "foo");

    assertThat(results).isNotNull();
    assertThat(results.results()).hasSize(1);
    assertThat(results.limited()).isFalse();
  }

  @Test
  @DisplayName("Search by an UserID")
  void getUserProfile() {
    stubFor(
        get(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID))
            .willReturn(
                okJson(
                    """
                    {
                      "displayname": "Test User",
                      "avatar_url": "mxc://matrix.org/abc123",
                      "m.tz": "Europe/London",
                      "m.example_field": "FooBar"
                    }
                    """)));

    UserProfile profile = client.userData().getUserProfile(USER_ID);

    assertThat(profile).isNotNull();
    assertThat(profile.additionalFields()).hasSize(1);
    assertThat(profile.additionalFields()).containsEntry("m.example_field", "FooBar");
  }

  @Test
  @DisplayName("Get value of a property from a User")
  void getUserProfileByProperty() {
    stubFor(
        get(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID + "/keyname"))
            .willReturn(okJson("{\"keyname\": \"valuename\"}")));

    String value = client.userData().getUserProfileByProperty(USER_ID, "keyname");

    assertThat(value).isEqualTo("valuename");
  }

  @Test
  @DisplayName("Set a key-value property for a User")
  void setUserProfileProperty() {
    stubFor(
        put(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID + "/keyname"))
            .willReturn(aResponse().withStatus(200)));

    client.userData().setUserProfileProperty(USER_ID, "keyname", "valuename");

    verify(
        putRequestedFor(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID + "/keyname"))
            .withRequestBody(equalToJson("{\"keyname\": \"valuename\"}")));
  }

  @Test
  @DisplayName("Delete a key-value property for a User")
  void deleteUserProfileProperty() {
    stubFor(
        delete(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID + "/keyname"))
            .willReturn(aResponse().withStatus(200)));

    client.userData().deleteUserProfileProperty(USER_ID, "keyname");

    verify(deleteRequestedFor(urlEqualTo("/_matrix/client/v3/profile/" + USER_ID + "/keyname")));
  }
}
