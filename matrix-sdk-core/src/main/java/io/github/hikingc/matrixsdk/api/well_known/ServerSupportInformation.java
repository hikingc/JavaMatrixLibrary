package io.github.hikingc.matrixsdk.api.well_known;

import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.net.URI;
import java.util.List;

public record ServerSupportInformation(List<Contact> contacts, URI supportPage) {

  public record Contact(String emailAddress, UserID userID, String role) {}
}
