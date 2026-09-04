package io.github.hikingc.matrixsdk.exceptions;

/// Thrown when the HTTP Client has been interrupted.
public class MatrixInterruptedException extends MatrixException {
  /// Constructs a [MatrixInterruptedException] with a message.
  ///
  /// @param message The detail message. The detail message is saved for later retrieval by the
  ///   getMessage() method.
  public MatrixInterruptedException(String message) {
    super(message);
  }

  /// Constructs a [MatrixInterruptedException] with a message.
  ///
  /// @param message The detail message. The detail message is saved for later retrieval by the
  ///   getMessage() method.
  /// @param cause the cause (which is saved for later retrieval by the getCause() method). (A null
  ///   value is permitted, and indicates that the cause is nonexistent or unknown.)
  public MatrixInterruptedException(String message, Throwable cause) {
    super(message, cause);
  }
}
