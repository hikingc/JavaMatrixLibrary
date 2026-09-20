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

import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import java.net.http.HttpClient;
import org.jspecify.annotations.Nullable;

public class MatrixClientBuilder {
  private DomainInformation domainInformation;
  private String authToken;
  private @Nullable HttpClient httpClient;

  public MatrixClientBuilder setdomainInformation(DomainInformation domainInformation) {
    this.domainInformation = domainInformation;
    return this;
  }

  public MatrixClientBuilder setAuthToken(String authToken) {
    this.authToken = authToken;
    return this;
  }

  public MatrixClientBuilder setHttpClient(@Nullable HttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  public MatrixClient createMatrixClient() {
    return new MatrixClient(domainInformation, authToken, httpClient);
  }
}
