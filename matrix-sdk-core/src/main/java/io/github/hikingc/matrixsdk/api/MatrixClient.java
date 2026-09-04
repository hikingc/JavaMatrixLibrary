package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.context.ClientContext;
import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import io.github.hikingc.matrixsdk.services.events.EventService;
import io.github.hikingc.matrixsdk.services.filtering.FilterService;
import io.github.hikingc.matrixsdk.services.rooms.RoomService;
import io.github.hikingc.matrixsdk.services.userdata.UserDataService;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import java.net.http.HttpClient;
import java.time.Duration;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// A [MatrixClient] provides all the functionality required to interact with a Matrix compliant
/// server.
@NullMarked
public class MatrixClient {
  private final Event event;
  private final Room roomService;
  private final UserData userDataService;
  private final Filter filter;

  MatrixClient(
      DiscoveryResponse discoveryResponse, String authToken, @Nullable HttpClient httpClient) {
    HttpClient client =
        httpClient == null
            ? HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()
            : httpClient;
    var context = new ClientContext(authToken, discoveryResponse);
    HttpTransport httpTransport = new HttpTransport(client);
    this.event = new EventService(context, httpTransport);
    this.roomService = new RoomService(context, httpTransport);
    this.userDataService = new UserDataService(context, httpTransport);
    this.filter = new FilterService(context, httpTransport);
  }

  /// Exposes the underlying [Event] service for operations.
  ///
  /// @return the underlying [Event] instance.
  public Event events() {
    return this.event;
  }

  /// Exposes the underlying [Room] service for operations.
  ///
  /// @return the underlying [Room] instance.
  public Room room() {
    return this.roomService;
  }

  /// Exposes the underlying [UserData] service for operations.
  ///
  /// @return the underlying [UserData] instance.
  public UserData userData() {
    return this.userDataService;
  }

  /// Exposes the underlying [Filter] service for operations.
  ///
  /// @return the underlying [Filter] instance.
  public Filter filter() {
    return this.filter;
  }
}
