package io.github.hikingc.matrixsdk.api.auth;

import java.io.IOException;
import java.net.URI;

/// Responsible for presenting the given authorization [URI] to the user, by whatever means is
/// appropriate for the host environment (opening a system browser, printing to console, embedding a
/// webview, etc.). Implementations do not need to block until the user completes login —
/// [io.github.hikingc.matrixsdk.api.MatrixAuth#performOAuthLogin(String, int, String)] handles
/// waiting for the callback.
@FunctionalInterface
public interface BrowserLauncher {
  void open(URI uri) throws IOException;
}
