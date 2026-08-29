package io.github.hikingc.matrixsdk.exceptions;

public class MatrixApiException extends MatrixException {
  private final int httpStatus;
  private final String errcode;
  private final String errorMessage;

  public MatrixApiException(int httpStatus, String errcode, String errorMessage) {
    super("HTTP " + httpStatus + " - " + errcode + ": " + errorMessage);
    this.httpStatus = httpStatus;
    this.errcode = errcode;
    this.errorMessage = errorMessage;
  }

  public MatrixApiException(String message, int httpStatus, Throwable cause) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.errcode = null;
    this.errorMessage = message;
  }

  public static MatrixApiException fromErrorResponse(int httpStatus, ErrorResponse resp) {
    return switch (resp.errCode()) {
      case "M_UNKNOWN_TOKEN", "M_MISSING_TOKEN", "M_FORBIDDEN" ->
          new MatrixAuthException(httpStatus, resp.errCode(), resp.error());
      case "M_LIMIT_EXCEEDED" ->
          new MatrixRateLimitException(
              httpStatus, resp.errCode(), resp.error(), resp.retryAfterMs());
      default -> new MatrixApiException(httpStatus, resp.errCode(), resp.error());
    };
  }

  // getters for httpStatus, errcode, errorMessage
}
