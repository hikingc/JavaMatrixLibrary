package io.github.hikingc.matrixsdk.services.filtering;

import io.github.hikingc.matrixsdk.api.Filter;
import io.github.hikingc.matrixsdk.api.filters.FilterDefinition;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.context.ClientContext;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import java.net.URI;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;

/// Main service implementation class of the [Filter] interface, providing the ability to create and
/// query filters.
@NullMarked
public class FilterService implements Filter {
  private static final String USER_FILTER_ENDPOINT = "/_matrix/client/v3/user/";

  private final HttpTransport httpTransport;
  private final ClientContext context;

  /// Service constructor to operate.
  ///
  /// @param context the [ClientContext] of the facade.
  /// @param httpTransport a [HttpTransport] object.
  public FilterService(ClientContext context, HttpTransport httpTransport) {
    this.context = context;
    this.httpTransport = httpTransport;
  }

  @Override
  public String publishFilter(UserID userId, FilterDefinition filter) {
    var serializedInputData = Mapper.writeValueAsString(filter);
    URI uri =
        httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            USER_FILTER_ENDPOINT + userId + "/filter",
            null);
    var responseBody = httpTransport.postRequest(uri, serializedInputData, context.token());

    return Mapper.getStringValueOfAJsonKey(responseBody, "filter_id");
  }

  @Override
  public FilterDefinition getFilter(UserID userId, String filterId) {
    Objects.requireNonNull(filterId, "Filter ID must not be null");
    URI uri =
        httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            USER_FILTER_ENDPOINT + userId + "/filter/" + filterId,
            null);
    return Mapper.getObjectFromString(
        httpTransport.getRequest(uri, context.token()), FilterDefinition.class);
  }
}
