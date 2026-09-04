package io.github.hikingc.matrixsdk.exceptions;

public class MatrixApiException extends MatrixException {
  private final int httpStatus;
  private final String errcode;
  private final String errorMessage;
  private final Long retryAfterMs; // non-null only for M_LIMIT_EXCEEDED

  public MatrixApiException(String errorCode, int httpStatus, String errorMessage) {
    this(errorCode, httpStatus, errorMessage, null);
  }

  public MatrixApiException(
      String errorCode, int httpStatus, String errorMessage, Long retryAfterMs) {
    super("HTTP " + httpStatus + " - " + errorCode + ": " + errorMessage);
    this.httpStatus = httpStatus;
    this.errcode = errorCode;
    this.errorMessage = errorMessage;
    this.retryAfterMs = retryAfterMs;
  }

  public MatrixApiException(String message, int httpStatus, Throwable cause) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.errcode = null;
    this.errorMessage = message;
    this.retryAfterMs = null;
  }

  public static MatrixApiException fromErrorResponse(int httpStatus, ErrorResponse resp) {
    Long retryAfter = "M_LIMIT_EXCEEDED".equals(resp.errCode()) ? resp.retryAfterMs() : null;
    return new MatrixApiException(resp.errCode(), httpStatus, resp.error(), retryAfter);
  }

  public boolean isAuthError() {
    return switch (errcode) {
      case "M_UNKNOWN_TOKEN", "M_MISSING_TOKEN", "M_FORBIDDEN" -> true;
      default -> false;
    };
  }

  public boolean isRateLimited() {
    return "M_LIMIT_EXCEEDED".equals(errcode);
  }

  public String errcode() {
    return errcode;
  }

  public String errorMessage() {
    return errorMessage;
  }

  public int httpStatus() {
    return httpStatus;
  }

  public Long retryAfterMs() {
    return retryAfterMs;
  }
}

// The following may not be returned from this API assuming the library is designed as per the spec.
// Aux comment 2 sept 2026
// M_BAD_JSON - We assume Jackson always creates valid JSON
// M_NOT_JSON - Jackson always creates JSON...
// M_MISSING_PARAM - Impossible if spec is followed
// M_INVALID_PARAM - Impossible if spec is followed
// M_MISSING_TOKEN - Services are designed to always set an authCode
