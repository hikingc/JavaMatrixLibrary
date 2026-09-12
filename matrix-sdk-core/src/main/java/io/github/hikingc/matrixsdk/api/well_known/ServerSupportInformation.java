package io.github.hikingc.matrixsdk.api.well_known;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;

import java.net.URI;
import java.util.List;

/// Represents server admin contact and support page of the domain.
///
/// @param contacts    ways to contact the server administrator.
///
/// At least one of `contacts` or `support_page` is required. If only `contacts` is set, it must contain at least one item.
/// @param supportPage the [URI] of a page to give users help specific to the homeserver, like extra login/registration steps.
///
/// At least one of `contacts` or `support_page` is required.
public record ServerSupportInformation(List<Contact> contacts, URI supportPage) {
    /// Information about the contact.
    ///
    /// @param emailAddress an email address to reach the administrator.
    ///
    /// At least one of `matrix_id` or `email_address` is required.
    /// @param userID       a [UserID] representing the administrator.
    ///
    /// It could be an account registered on a different homeserver so the administrator can be contacted when the homeserver is down.
    ///
    /// At least one of `matrix_id` or `email_address` is required.
    /// @param role         an informal description of what the contact methods are used for.
    ///
    /// `m.role.admin` is a catch-all role for any queries and `m.role.security` is intended for sensitive requests.
    ///  Unspecified roles are permitted through the use of Namespaced Identifiers.
    public record Contact(String emailAddress, UserID userID, @JsonProperty(required = true) String role) {
    }
}
// Role is not an enum, I don't know how to make unknown incoming data be "serialized" into enums...