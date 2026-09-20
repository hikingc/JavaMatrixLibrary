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
import io.github.hikingc.matrixsdk.context.ClientContext;
import io.github.hikingc.matrixsdk.services.events.EventService;
import io.github.hikingc.matrixsdk.services.filtering.FilterService;
import io.github.hikingc.matrixsdk.services.rooms.RoomService;
import io.github.hikingc.matrixsdk.services.userdata.UserDataService;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import java.net.http.HttpClient;
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
      DomainInformation domainInformation, String authToken, @Nullable HttpClient httpClient) {
    var context = new ClientContext(authToken, domainInformation);
    HttpTransport httpTransport = new HttpTransport(httpClient);
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
