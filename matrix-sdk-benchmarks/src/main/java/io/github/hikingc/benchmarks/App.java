package io.github.hikingc.benchmarks;

import io.github.hikingc.matrixsdk.api.MatrixAuth;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.events.RoomInfo;
import io.github.hikingc.matrixsdk.api.events.queries.QueryParametersSync;
import io.github.hikingc.matrixsdk.api.events.sync.Sync;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.net.URI;
import java.net.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
  private static final Logger log = LoggerFactory.getLogger(App.class);

  static void main() {
    HttpClient httpClient =
        HttpClient.newBuilder().build(); // Create a client, this will do for this example.
    MatrixAuth auth =
        new MatrixAuth(URI.create("https://kde.org"), httpClient); // Set the URI and the client
    TokenMetadata res =
        auth.performOAuthLogin(
            "clienttest", 8080, "defgagagea"); // Perform interactive login (browser needed)

    MatrixClient client =
        new MatrixClientBuilder()
            .setDiscoveryResponse(auth.fetchWellKnown()) // Get .well_known
            .setAuthToken(res.accessToken()) // Set up the code
            .createMatrixClient();
    Sync sync =
        client
            .events()
            .sync(
                new QueryParametersSync(
                    "{\"room\":{\"timeline\":{\"unread_thread_notifications\":true,\"limit\":20},\"state\":{\"lazy_load_members\":true}}}",
                    true,
                    null,
                    null,
                    0,
                    true)); // Perform operations.
    log.info(String.valueOf(sync)); // This endpoint will take a while, you have been warned...

    try {
      RoomInfo roomInfo =
          client
              .events()
              .getInitialSync(
                  RoomID.create(
                      "!foobar:example.org")); // Type safe identifiers, will crash if given wrong
      // format.
      log.info(roomInfo.visibility());
    } catch (IllegalArgumentException e) {
      log.info("Room id is bad!, Reason: {}", e.getMessage());
    }
  }
}
