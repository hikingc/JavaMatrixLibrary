package io.github.hikingc;

import io.github.hikingc.matrixsdk.api.MatrixAuth;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.events.RoomInfo;
import io.github.hikingc.matrixsdk.api.events.queries.QueryParametersSync;
import io.github.hikingc.matrixsdk.api.events.sync.Sync;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import java.net.URI;
import java.net.http.HttpClient;

/// This is a basic example of the sdk.
///
/// The common workflow is as follows: An authentication class is instantiated, upon which we
/// retrieve keys to instantiate the client and metadata for the client to hold on to.
///
/// This example showcases 2 endpoints,`/sync` and `/initialSync`.
public class AppInit {

  static void main() {
    HttpClient httpClient =
        HttpClient.newBuilder().build(); // Create a client, this will do for this example.
    MatrixAuth auth =
        new MatrixAuth(URI.create("https://example.org"), httpClient); // Set the URI and the client
    TokenMetadata res =
        auth.performOAuthLogin(
            "clienttest", 8080, "defgagagea"); // Perform interactive login (browser needed)

    MatrixClient client =
        new MatrixClientBuilder()
            .setDiscoveryResponse(auth.fetchWellKnown()) // Get .well_known
            .setAuthToken(res.accessToken()) // Set up the code
            .createMatrixClient();
    try {

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
    } catch (MatrixException e) {
      System.out.println("Something went wrong");
    }

    try {
      RoomInfo roomInfo =
          client
              .events()
              .getInitialSync(
                  RoomID.create(
                      "!foobar:example.org")); // Type safe identifiers, will crash if given wrong
      // format.
    } catch (IllegalArgumentException e) {
      System.out.println("Room ID is invalid: " + e.getMessage());
    }
  }
}
