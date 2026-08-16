package io.github.hikingc.matrixsdk.api.identifiers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/// Shared static validation helpers for Matrix identifiers.
///
/// @see <a href="https://spec.matrix.org/v1.19/appendices/#identifier-grammar">Matrix definitions
///   and Grammar of Identifiers</a>
public final class Validator {
  /// The maximum value of bytes as defined in the specification.
  static final int MAX_BYTES = 255;

  private Validator() {}

  /// Shared validation for Matrix identifiers of the form `<sigil><opaqueId>:<server_name>` (room
  /// ids, user ids, room aliases).
  ///
  /// Validates the sigil, presence of a separating colon, overall byte length, Unicode
  /// well-formedness, and that a server name actually follows the colon. Optionally restricts the
  /// opaqueId to alphanumeric characters.
  ///
  /// @param value the raw [String] to be evaluated.
  /// @param sigil the prefix of the ID.
  /// @param name it's name
  /// @param restrictLocalpartToAlphanumeric whether it should be evaluated against only
  ///   alphanumeric characters
  static void validateSigilId(
      String value, char sigil, String name, boolean restrictLocalpartToAlphanumeric) {
    Objects.requireNonNull(value, name + " must not be null");

    if (value.isEmpty()) {
      throw new IllegalArgumentException(name + " must not be empty");
    }

    if (value.getBytes(StandardCharsets.UTF_8).length > MAX_BYTES) {
      throw new IllegalArgumentException(name + " exceeds " + MAX_BYTES + " bytes");
    }

    validateSigil(value, sigil, name);

    int firstColon = value.indexOf(':');
    if (firstColon < 0) {
      throw new IllegalArgumentException(
          name + " must contain ':' separating opaqueId from server name");
    }

    // Check if we have strings with nothing between the sigil and the :
    if (firstColon == 1) {
      throw new IllegalArgumentException(name + " must not have an empty opaqueId");
    }

    String localPart = value.substring(1, firstColon);
    String serverName = value.substring(firstColon + 1);

    // NUL is banned everywhere in the identifier; ':' is banned within the
    // opaqueId specifically (the separator colon is exactly firstColon, so it's
    // excluded from localPart already — serverName may legitimately contain ':'
    // for IPv6 literals or an explicit port).
    validateCodePoints(localPart, name, true);
    validateCodePoints(serverName, name, false);

    if (!validateDomain(serverName)) {
      throw new IllegalArgumentException(name + " must contain a valid server name after ':'");
    }
    if (restrictLocalpartToAlphanumeric && !localPart.matches("[a-zA-Z0-9]+")) {
      throw new IllegalArgumentException(
          name + " opaqueId should only contain alphanumeric characters");
    }
  }

  static void validateSigil(String value, char sigil, String name) {
    if (value.charAt(0) != sigil) {
      throw new IllegalArgumentException(name + " must start with '" + sigil + "'");
    }
  }

  /// The matrix specification defines as compliant any codepoint that contains valid non-surrogate
  /// Unicode code points, except `NUL (U+0000)`, and, within the opaqueId segment, `:`.
  ///
  /// @param segment the substring being validated (either the opaqueId or the server name).
  /// @param name the type of the ID being evaluated, used only for error messages.
  /// @param banColon whether ':' is disallowed in this segment (true for opaqueId, false for server
  ///   name, which may legitimately contain ':' for IPv6 literals or a port).
  static void validateCodePoints(String segment, String name, boolean banColon) {
    segment
        .codePoints()
        .forEach(
            cp -> {
              if (!Character.isValidCodePoint(cp)) {
                throw new IllegalArgumentException(
                    "%s contains an invalid Unicode code point: U+%04X".formatted(name, cp));
              }
              if (cp >= 0xD800 && cp <= 0xDFFF) {
                throw new IllegalArgumentException(
                    "%s contains a lone surrogate code point: U+%04X".formatted(name, cp));
              }
              if (cp == 0x0000) {
                throw new IllegalArgumentException(name + " must not contain NUL");
              }
              if (banColon && cp == ':') {
                throw new IllegalArgumentException(name + " opaqueId must not contain ':'");
              }
              if (Character.isWhitespace(cp)) {
                throw new IllegalArgumentException("%s contains whitespace.".formatted(name));
              }
            });
  }

  /// Dirty check to ensure the servername is valid
  ///
  /// @param serverName an IPv4, IPv6, or valid hostname (with or without a port).
  /// @return whether if it's a valid domain based on [URI] rules
  static boolean validateDomain(String serverName) {
    if (serverName == null || serverName.isBlank()) {
      return false;
    }

    try {
      URI uri = new URI("scheme://" + serverName);

      String host = uri.getHost();
      int port = uri.getPort();

      if (host == null) {
        return false;
      }

      if (uri.getPath() != null && !uri.getPath().isEmpty()) return false;
      if (uri.getUserInfo() != null || uri.getQuery() != null || uri.getFragment() != null)
        return false;

      if (serverName.contains(":") && !host.startsWith("[")) {
        return port == -1 || (port >= 1 && port <= 65535);
      }

      return true;
    } catch (URISyntaxException _) {
      return false;
    }
  }
}
