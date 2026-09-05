package io.github.hikingc.benchmarks;

import io.github.hikingc.matrixsdk.api.MatrixAuth;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.auth.TokenMetadata;
import io.github.hikingc.matrixsdk.api.events.queries.QueryParametersSync;
import io.github.hikingc.matrixsdk.api.events.sync.Sync;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Fork(0) // no separate JVM forks — run in-process
@Warmup(iterations = 0) // skip warmup entirely
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.SingleShotTime) // run the method exactly once per iteration
public class App {
  @Benchmark
  public void doSync() {
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
  }

  static void main() throws RunnerException {
    Options opt = new OptionsBuilder().include(App.class.getSimpleName()).forks(1).build();

    new Runner(opt).run();
  }
}
