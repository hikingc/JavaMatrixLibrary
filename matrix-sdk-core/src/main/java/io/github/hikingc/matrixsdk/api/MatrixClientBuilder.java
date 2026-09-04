package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import org.jspecify.annotations.Nullable;

import java.net.http.HttpClient;

public class MatrixClientBuilder{
    private DiscoveryResponse discoveryResponse;
    private String authToken;
    private @Nullable HttpClient httpClient;

    public MatrixClientBuilder setDiscoveryResponse(DiscoveryResponse discoveryResponse) {
        this.discoveryResponse = discoveryResponse;
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
        return new MatrixClient(discoveryResponse, authToken, httpClient);
    }
}