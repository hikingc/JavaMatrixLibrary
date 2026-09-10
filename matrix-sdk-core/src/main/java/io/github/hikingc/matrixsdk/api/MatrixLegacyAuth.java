package io.github.hikingc.matrixsdk.api;

import io.github.hikingc.matrixsdk.api.auth.Versions;
import io.github.hikingc.matrixsdk.api.auth.WhoAmI;
import org.jspecify.annotations.Nullable;

public class MatrixLegacyAuth implements Auth {
  @Override
  public WhoAmI getCurrentAccountInformation(String token) {
    return null;
  }

  @Override
  public Versions getVersions(@Nullable String token) {
    return null;
  }
}
