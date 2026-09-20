/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.exceptions;

/// Thrown to indicate that the code has attempted to process a serialization task to which it has
/// failed.
public class MatrixSerializationException extends MatrixException {
  /// Constructs a [MatrixSerializationException] with a message.
  ///
  /// @param message The detail message. The detail message is saved for later retrieval by the
  ///   getMessage() method.
  public MatrixSerializationException(String message) {
    super(message);
  }

  /// Constructs a [MatrixSerializationException] with a message and a throwable.
  ///
  /// @param message the detail message (which is saved for later retrieval by the getMessage()
  ///   method).
  /// @param cause the cause (which is saved for later retrieval by the getCause() method). (A null
  ///   value is permitted, and indicates that the cause is nonexistent or unknown.)
  public MatrixSerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
