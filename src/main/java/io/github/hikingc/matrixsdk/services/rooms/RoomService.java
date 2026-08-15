package io.github.hikingc.matrixsdk.services.rooms;

import io.github.hikingc.matrixsdk.api.Room;
import io.github.hikingc.matrixsdk.api.identifiers.RoomAlias;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.api.identifiers.Validator;
import io.github.hikingc.matrixsdk.api.rooms.*;
import io.github.hikingc.matrixsdk.api.rooms.models.ResolvedAlias;
import io.github.hikingc.matrixsdk.api.rooms.models.RoomSummary;
import io.github.hikingc.matrixsdk.api.rooms.queries.JoinRoomRequest;
import io.github.hikingc.matrixsdk.api.rooms.queries.VisibilityRoomType;
import io.github.hikingc.matrixsdk.context.ClientContext;
import io.github.hikingc.matrixsdk.exceptions.MatrixException;
import io.github.hikingc.matrixsdk.exceptions.MatrixIOException;
import io.github.hikingc.matrixsdk.services.utils.HttpTransport;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import java.net.URI;
import java.util.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;

/// Main service implementation class of the Room interface, providing all the required endpoints
/// and records to perform activities such as kicking, banning, listing of, and creation of rooms.
@NullMarked
public class RoomService implements Room {

  /// Common return field value by many responses.
  public static final String ROOM_ID = "room_id";

  /// Common endpoint for many Room events.
  private static final String ROOM_ENDPOINT = "/_matrix/client/v3/rooms/";

  /// Common endpoint for many Directory events.
  private static final String DIRECTORY_ENDPOINT = "/_matrix/client/v3/directory/list/room/";

  /// Common endpoint for other Directory events.
  private static final String DIRECTORY_ENDPOINT_ROOM = "/_matrix/client/v3/directory/room/";

  private final HttpTransport httpTransport = new HttpTransport(10);
  private final ClientContext context;

  /// Service constructor to operate
  ///
  /// @param context the [ClientContext] of the facade
  public RoomService(ClientContext context) {
    this.context = context;
  }

  @Override
  public String create(InitialRoomConfiguration configuration) {

    String jsonPayload = Mapper.writeValueAsString(configuration);

    String responseBody;
    responseBody =
        httpTransport.postRequest(
            URI.create(
                context.discoveryResponse().homeserver().baseUrl()
                    + "/_matrix/client/v3/createRoom"),
            jsonPayload,
            context.token());

    return Mapper.getStringValueOfAJsonKey(responseBody, ROOM_ID);
  }

  @Override
  public ResolvedAlias resolveAlias(RoomAlias roomAlias) {
    URI uri =
        httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            DIRECTORY_ENDPOINT_ROOM + roomAlias,
            null);

    var responseBody = httpTransport.getRequest(uri, context.token());
    return Mapper.getObjectFromString(responseBody, ResolvedAlias.class);
  }

  @Override
  public void setAlias(RoomAlias roomAlias, RoomID roomId) {
    URI uri =
        httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            DIRECTORY_ENDPOINT_ROOM + roomAlias,
            null);

    Map<String, Object> map = new HashMap<>();
    map.put(ROOM_ID, roomId);

    httpTransport.putRequest(uri, Mapper.createObjectFromMap(map), context.token());
  }

  @Override
  public void deleteAlias(RoomAlias roomAlias) {
    URI uri =
        httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            DIRECTORY_ENDPOINT_ROOM + roomAlias,
            null);
    httpTransport.deleteRequest(uri, context.token());
  }

  @Override
  public List<String> getAliasesOfARoom(RoomID roomId) {

    String response =
        httpTransport.getRequest(
            URI.create(
                context.discoveryResponse().homeserver().baseUrl()
                    + ROOM_ENDPOINT
                    + roomId
                    + "/aliases"),
            context.token());

    JsonNode aliases;
    List<String> aliasesList = new ArrayList<>();
    try {
      aliases = Mapper.getInstance().readTree(response).get("aliases");
      for (JsonNode alias : aliases) {
        aliasesList.add(alias.stringValue());
      }
    } catch (JacksonException e) {
      throw new MatrixIOException("Failed to deserialize response JSON", e);
    }
    return aliasesList;
  }

  @Override
  public List<String> getJoinedRooms() {
    String response =
        httpTransport.getRequest(
            URI.create(
                context.discoveryResponse().homeserver().baseUrl()
                    + "/_matrix"
                    + "/client/v3/joined_rooms"),
            context.token());

    return Mapper.getListFromAJsonKey(response, "joined_rooms", String.class);
  }

  @Override
  public void inviteUser(RoomID roomId, RoomMembershipRequest event) {
    String serializedInputData = Mapper.writeValueAsString(event);
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl()
                + ROOM_ENDPOINT
                + roomId
                + "/invite"),
        serializedInputData,
        this.context.token());
  }

  @Override
  public String joinByRoomIdOrAliasIfAllowed(
      Validator roomIdOrAlias, JoinRoomRequest request, List<String> via) {
    if (Objects.requireNonNull(roomIdOrAlias) instanceof UserID) {
      throw new IllegalArgumentException("Wrong format type");
    }

    Map<String, Object> params = new HashMap<>();
    params.put("via", via);
    URI uri =
        this.httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            "/_matrix/client/v3/join/" + roomIdOrAlias,
            params);
    String serializedInputData = Mapper.writeValueAsString(request);
    var responseBody = httpTransport.postRequest(uri, serializedInputData, context.token());
    return Mapper.getStringValueOfAJsonKey(responseBody, ROOM_ID);
  }

  @Override
  public String joinByRoomIdIfAllowed(RoomID roomId, JoinRoomRequest request, List<String> via) {
    Map<String, Object> params = new HashMap<>();
    params.put("via", via);
    URI uri =
        this.httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            ROOM_ENDPOINT + roomId + "/join",
            params);
    String serializedInputData = Mapper.writeValueAsString(request);
    var responseBody = httpTransport.postRequest(uri, serializedInputData, context.token());
    return Mapper.getStringValueOfAJsonKey(responseBody, ROOM_ID);
  }

  @Override
  public String knockOn(Validator roomIdOrAlias, String reason, List<String> via) {
    if (Objects.requireNonNull(roomIdOrAlias) instanceof UserID) {
      throw new MatrixException("Wrong format type");
    }

    URI uri =
        this.httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            "/_matrix/client/v3/knock/" + roomIdOrAlias,
            Map.ofEntries(Map.entry("via", via)));
    Map<String, Object> map = new HashMap<>();
    map.put("reason", reason);

    String responseBody =
        httpTransport.postRequest(uri, Mapper.createObjectFromMap(map), context.token());
    try {
      return Mapper.getStringValueOfAJsonKey(responseBody, ROOM_ID);
    } catch (JacksonException e) {
      throw new MatrixIOException("Failed to parse Matrix response JSON ", e);
    }
  }

  @Override
  public void forget(RoomID roomId) {
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl()
                + ROOM_ENDPOINT
                + roomId
                + "/forget"),
        null,
        this.context.token());
  }

  @Override
  public void leave(RoomID roomId) {
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl() + ROOM_ENDPOINT + roomId + "/leave"),
        null,
        this.context.token());
  }

  @Override
  public void kick(RoomID roomId, RoomMembershipRequest event) {
    String serializedInputData = Mapper.writeValueAsString(event);
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl() + ROOM_ENDPOINT + roomId + "/kick"),
        serializedInputData,
        this.context.token());
  }

  @Override
  public void ban(RoomID roomId, RoomMembershipRequest event) {
    String serializedInputData = Mapper.writeValueAsString(event);
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl() + ROOM_ENDPOINT + roomId + "/ban"),
        serializedInputData,
        this.context.token());
  }

  @Override
  public void unban(RoomID roomId, RoomMembershipRequest event) {
    String responseBody = Mapper.writeValueAsString(event);
    httpTransport.postRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl() + ROOM_ENDPOINT + roomId + "/unban"),
        responseBody,
        this.context.token());
  }

  @Override
  public String getRoomDirectoryVisibilityType(RoomID roomId) {
    var responseBody =
        httpTransport.getRequest(
            URI.create(
                context.discoveryResponse().homeserver().baseUrl() + DIRECTORY_ENDPOINT + roomId),
            null);
    return Mapper.getStringValueOfAJsonKey(responseBody, "visibility");
  }

  @Override
  public void setRoomDirectoryVisibilityType(RoomID roomId, VisibilityRoomType roomType) {
    Map<String, Object> map = new HashMap<>();
    map.put("visibility", roomType);

    httpTransport.putRequest(
        URI.create(
            context.discoveryResponse().homeserver().baseUrl() + DIRECTORY_ENDPOINT + roomId),
        Mapper.createObjectFromMap(map),
        this.context.token());
  }

  @Override
  public PublicRoomDirectory getPublishedRoomDirectory(
      Integer limit, @Nullable String server, @Nullable String since) {
    Map<String, Object> params = new HashMap<>();
    params.put("limit", String.valueOf(limit));
    if (server != null) {
      params.put("server", server);
    }
    if (since != null) {
      params.put("since", since);
    }
    URI uri =
        this.httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            "/_matrix/client/v3/publicRooms",
            params);
    var responseBody = httpTransport.getRequest(uri, context.token());

    return Mapper.getObjectFromString(responseBody, PublicRoomDirectory.class);
  }

  @Override
  public PublicRoomDirectory getPublishedRoomDirectory(PublicRoomRequest request) {
    String serializedInputData = Mapper.writeValueAsString(request);

    var responseBody =
        httpTransport.postRequest(
            URI.create(
                context.discoveryResponse().homeserver().baseUrl()
                    + "/_matrix/client/v3/publicRooms"),
            serializedInputData,
            context.token());
    return Mapper.getObjectFromString(responseBody, PublicRoomDirectory.class);
  }

  @Override
  public RoomSummary getRoomSummary(Validator roomIdOrAlias, List<String> via) {
    if (Objects.requireNonNull(roomIdOrAlias) instanceof UserID) {
      throw new MatrixException("Wrong format type");
    }

    Map<String, Object> args = new HashMap<>();
    args.put("via", via);

    URI uri =
        this.httpTransport.generateEncodedURI(
            context.discoveryResponse().homeserver().baseUrl(),
            "/_matrix/client/v1/room_summary/" + roomIdOrAlias,
            args);
    var responseBody = httpTransport.getRequest(uri, context.token());

    return Mapper.getObjectFromString(responseBody, RoomSummary.class);
  }
}
