package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.auth.Versions;
import io.github.hikingc.matrixsdk.api.auth.WhoAmI;
import io.github.hikingc.matrixsdk.api.well_known.DomainInformation;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;

@NullMarked
public class BaseAuth implements Auth {
    private final HttpTransport httpTransport;
    private final DomainInformation domainInformation;

    public BaseAuth(@Nullable HttpClient httpClient, DomainInformation domainInformation) {
        this.httpTransport = new HttpTransport(httpClient);
        this.domainInformation = domainInformation;
    }

    @Override
    public WhoAmI getCurrentAccountInformation(String token) {
        var response =
                httpTransport.getRequest(
                        URI.create(
                                domainInformation.homeserver().baseUrl() + "/_matrix/client/v3/account/whoami"),
                        token);
        return Mapper.getObjectFromInputStream(response, WhoAmI.class);
    }

    @Override
    public Versions getVersions(@Nullable String authToken) {
        var response =
                httpTransport.getRequest(
                        URI.create(domainInformation.homeserver().baseUrl() + "/_matrix/client/versions"),
                        authToken);
        return Mapper.getObjectFromInputStream(response, Versions.class);
    }
}
