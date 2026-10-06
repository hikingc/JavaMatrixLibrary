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

import io.github.hikingc.matrixsdk.api.auth.LegacyMetadata;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;

public class MatrixLegacyAuth extends BaseAuth {

    private final Logger logger = LoggerFactory.getLogger(MatrixLegacyAuth.class);
    private final HttpTransport httpTransport;
    private final DomainInformation domainInformation;

    /// Instantiates the authenticator and checks if the supplied `baseUrl` is valid.
    ///
    /// @param httpClient        if supplied, the [HttpClient] that the library will use to perform calls,
    ///   otherwise the library will create one.
    /// @param domainInformation required to perform calls.
    /// @throws MatrixException if the supplied `baseUrl` is not a matrix server.
    public MatrixLegacyAuth(@Nullable HttpClient httpClient, DomainInformation domainInformation) {
        super(httpClient, domainInformation);
        this.httpTransport = new HttpTransport(httpClient);
        this.domainInformation = domainInformation;
        this.getVersions(null);
    }

    /// Permits user authentication with an `access_token` for use with [MatrixClient].
    ///
    /// @param username a valid [UserID]
    /// @param password the user's password
    /// @return Authentication metadata and access tokens.
    public LegacyMetadata performAuthLogin(UserID username, String password) {
        String payload = """
                {
                  "type": "m.login.password",
                  "identifier": {
                    "type": "m.id.user",
                    "user": "%s"
                  },
                  "password": "%s"
                }
                """.formatted(username, password);

        try (var response = httpTransport.postRequest(URI.create(domainInformation.homeserver().baseUrl() + "/_matrix/client/v3/login"), payload.getBytes(), null)) {
            return Mapper.getObjectFromInputStream(response, LegacyMetadata.class);
        } catch (IOException e) {
            throw new MatrixIOException("Failed to perform auth login", e);
        }
    }
}
