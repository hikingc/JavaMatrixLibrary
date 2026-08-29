package io.github.hikingc.matrixsdk.exceptions;

public class MatrixRateLimitException extends MatrixApiException {
  public MatrixRateLimitException(int httpStatus, String s, String error, Object retryAfterMs) {
    super();
  }
}
