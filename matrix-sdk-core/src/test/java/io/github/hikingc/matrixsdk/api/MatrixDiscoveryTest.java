package io.github.hikingc.matrixsdk.api;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WireMockTest
class MatrixDiscoveryTest {
    private static final URI baseUrl = URI.create("https://example.org");
    private static MatrixDiscovery matrixDiscovery;

    @BeforeEach
    void setUp() {
        matrixDiscovery = new MatrixDiscovery(baseUrl, null);
    }

    @Test
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
    void getFetchSupportServer_ReturnAnObject() {
        stubFor(
                get(urlEqualTo("/.well-known/matrix/client"))
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
        var response = matrixDiscovery.fetchPolicyServer();
        assertThat(response).isNotNull();
    }

    @Test
    void getFetchPolicyServer_ReturnAnObject() {
        stubFor(
                get(urlEqualTo("/.well-known/matrix/client"))
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
        var response = matrixDiscovery.fetchPolicyServer();
        assertThat(response).isNotNull();
    }
}
