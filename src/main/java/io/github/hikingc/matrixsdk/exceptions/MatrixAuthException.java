package io.github.hikingc.matrixsdk.exceptions;

public class MatrixAuthException extends MatrixApiException {
  public MatrixAuthException(int httpStatus, String s, String error) {
    super(httpStatus, s, error);
  }
}
