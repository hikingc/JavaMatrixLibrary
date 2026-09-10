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
