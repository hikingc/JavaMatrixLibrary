package io.github.hikingc.matrixsdk.services.events;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.hikingc.matrixsdk.api.MatrixClient;
import io.github.hikingc.matrixsdk.api.MatrixClientBuilder;
import io.github.hikingc.matrixsdk.api.events.*;
import io.github.hikingc.matrixsdk.api.events.matrix.*;
import io.github.hikingc.matrixsdk.api.events.matrix.call.*;
import io.github.hikingc.matrixsdk.api.events.matrix.key.*;
import io.github.hikingc.matrixsdk.api.events.matrix.room.*;
import io.github.hikingc.matrixsdk.api.events.matrix.room.message.*;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceChild;
import io.github.hikingc.matrixsdk.api.events.matrix.space.SpaceParent;
import io.github.hikingc.matrixsdk.api.events.queries.ChronologicalDirection;
import io.github.hikingc.matrixsdk.api.events.queries.Membership;
import io.github.hikingc.matrixsdk.api.events.queries.QueryParametersMessages;
import io.github.hikingc.matrixsdk.api.events.queries.QueryParametersSync;
import io.github.hikingc.matrixsdk.api.events.sync.Sync;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import io.github.hikingc.matrixsdk.context.DiscoveryResponse;
import io.github.hikingc.matrixsdk.exceptions.MatrixSerializationException;
import io.github.hikingc.matrixsdk.services.utils.Mapper;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@WireMockTest
class EventServiceTest {

  public static final RoomID ROOM_ID = RoomID.create("!room:example.org");
  private static final String AUTH_TOKEN = "1234";
  private static DiscoveryResponse DISCOVERY_RESPONSE;
  private static MatrixClient client;

  @BeforeAll
  static void setUpDiscovery(WireMockRuntimeInfo wireMockRuntimeInfo) {
    DISCOVERY_RESPONSE =
        new DiscoveryResponse(
            new DiscoveryResponse.HomeserverInfo(wireMockRuntimeInfo.getHttpBaseUrl()), null, null);
  }

  private static Result getResult(Path tempDir) throws IOException {
    String roomMessageType = "m.room.message";
    String expectedEventId = "$h29asdf8q348hju9a:matrix.org";

    String serverName = "matrix.org";
    String mediaId = "fakeMediaId123";
    URI mockMxcUri = URI.create("mxc://" + serverName + "/" + mediaId);

    Path tempFile = tempDir.resolve("file.txt");
    Files.writeString(tempFile, "Test");
    return new Result(
        ROOM_ID, roomMessageType, expectedEventId, serverName, mediaId, mockMxcUri, tempFile);
  }

  @BeforeEach
  void createClient() {
    client =
        new MatrixClientBuilder()
            .setDiscoveryResponse(DISCOVERY_RESPONSE)
            .setAuthToken(AUTH_TOKEN)
            .createMatrixClient();
  }

  @Test
  void getEvent_WithACorrectPayload_ThenReturnAClientEvent() {
    String eventId = "$143273582443PhrSn:example.org";
    stubFor(
        get("/_matrix/client/v3/rooms/" + ROOM_ID + "/event/" + eventId)
            .willReturn(
                okJson(
                    """
                        {
                          "content": {
                            "body": "This is an example text message",
                            "format": "org.matrix.custom.html",
                            "formatted_body": "<b>This is an example text message</b>",
                            "msgtype": "m.text"
                          },
                          "event_id": "$143273582443PhrSn:example.org",
                          "origin_server_ts": 1432735824653,
                          "room_id": "!636q39766251:matrix.org",
                          "sender": "@example:example.org",
                          "type": "m.room.message",
                          "unsigned": {
                            "age": 1234,
                            "membership": "join"
                          }
                        }
                        """)));

    var response = client.events().getEvent(ROOM_ID, eventId);
    assertThat(response).isNotNull();
    assertThat(response.eventId()).isEqualTo(EventID.create("$143273582443PhrSn:example.org"));
  }

  @Test
  void getJoinedMembers_WithACorrectPayload_ThenReturnARoomMembers() {
    stubFor(
        get("/_matrix/client/v3/rooms/" + ROOM_ID + "/joined_members")
            .willReturn(
                okJson(
                    """
                        {
                          "joined": {
                            "@bar:example.com": {
                              "avatar_url": "mxc://riot.ovh/printErCATzZijQsSDWorRaK",
                              "display_name": "Bar"
                            }
                          }
                        }

                        """)));
    var response = client.events().getJoinedMembers(ROOM_ID);
    assertThat(response).isNotNull();
  }

  @Test()
  void getMembers_WithACorrectPayload_thenReturnAListOfClientEvent() {
    final String TOKEN = "FAKE_PAGINATION_TOKEN";

    stubFor(
        get(urlPathEqualTo("/_matrix/client/v3/rooms/" + ROOM_ID + "/members"))
            .withQueryParams(
                Map.ofEntries(
                    Map.entry("at", equalTo(TOKEN)),
                    Map.entry("membership", equalTo(Membership.JOIN.getValue())),
                    Map.entry("not_membership", equalTo(Membership.JOIN.getValue()))))
            .willReturn(
                okJson(
                    """
                        {
                          "chunk": [
                            {
                              "content": {
                                "avatar_url": "mxc://example.org/SEsfnsuifSDFSSEF",
                                "displayname": "Alice Margatroid",
                                "membership": "join",
                                "reason": "Looking for support"
                              },
                              "event_id": "$143273582443PhrSn:example.org",
                              "origin_server_ts": 1432735824653,
                              "room_id": "!636q39766251:example.com",
                              "sender": "@alice:example.org",
                              "state_key": "@alice:example.org",
                              "type": "m.room.member",
                              "unsigned": {
                                "age": 1234,
                                "membership": "join"
                              }
                            }
                          ]
                        }

                        """)));
    var response = client.events().getMembers(ROOM_ID, TOKEN, Membership.JOIN, Membership.JOIN);
    assertThat(response).isNotNull();
    assertThat(response).hasSize(1);
    assertThat(response.getFirst().content().avatarUrl())
        .isEqualTo(URI.create("mxc://example.org/SEsfnsuifSDFSSEF"));
    assertThat(response.getFirst().content().displayname()).isEqualTo("Alice Margatroid");
    assertThat(response.getFirst().content().membership()).isEqualTo(Membership.JOIN);
    assertThat(response.getFirst().content().reason()).isEqualTo("Looking for support");
    assertThat(response.getFirst().eventId())
        .isEqualTo(EventID.create("$143273582443PhrSn:example.org"));
    assertThat(response.getFirst().originServerTs()).isEqualTo(1432735824653L);
    assertThat(response.getFirst().eventId())
        .isEqualTo(EventID.create("$143273582443PhrSn:example.org"));
  }

  @Test
  void getStateEvents_WithACorrectPaYload_thenReturnAListOfClientEvent() {
    stubFor(
        get("/_matrix/client/v3/rooms/" + ROOM_ID + "/state")
            .willReturn(
                okJson(
                    """
                        [
                          {
                            "content": {
                              "join_rule": "public"
                            },
                            "event_id": "$143273582443PhrSn:example.org",
                            "origin_server_ts": 1432735824653,
                            "room_id": "!636q39766251:example.com",
                            "sender": "@example:example.org",
                            "state_key": "",
                            "type": "m.room.join_rules",
                            "unsigned": {
                              "age": 1234,
                              "membership": "join"
                            }
                          },
                          {
                            "content": {
                              "avatar_url": "mxc://example.org/SEsfnsuifSDFSSEF",
                              "displayname": "Alice Margatroid",
                              "membership": "join",
                              "reason": "Looking for support"
                            },
                            "event_id": "$143273582443PhrSn:example.org",
                            "origin_server_ts": 1432735824653,
                            "room_id": "!636q39766251:example.com",
                            "sender": "@alice:example.org",
                            "state_key": "@alice:example.org",
                            "type": "m.room.member",
                            "unsigned": {
                              "age": 1234,
                              "membership": "join"
                            }
                          },
                          {
                            "content": {
                              "m.federate": true,
                              "predecessor": {
                                "event_id": "$something:example.org",
                                "room_id": "!oldroom:example.org"
                              },
                              "room_version": "11"
                            },
                            "event_id": "$143273582443PhrSn:example.org",
                            "origin_server_ts": 1432735824653,
                            "room_id": "!636q39766251:example.com",
                            "sender": "@example:example.org",
                            "state_key": "",
                            "type": "m.room.create",
                            "unsigned": {
                              "age": 1234,
                              "membership": "join"
                            }
                          },
                          {
                            "content": {
                              "ban": 50,
                              "events": {
                                "m.room.name": 100,
                                "m.room.power_levels": 100
                              },
                              "events_default": 0,
                              "invite": 50,
                              "kick": 50,
                              "notifications": {
                                "room": 20
                              },
                              "redact": 50,
                              "state_default": 50,
                              "users": {
                                "@example:localhost": 100
                              },
                              "users_default": 0
                            },
                            "event_id": "$143273582443PhrSn:example.org",
                            "origin_server_ts": 1432735824653,
                            "room_id": "!636q39766251:example.com",
                            "sender": "@example:example.org",
                            "state_key": "",
                            "type": "m.room.power_levels",
                            "unsigned": {
                              "age": 1234,
                              "membership": "join"
                            }
                          }
                        ]

                        """)));
    var response = client.events().getStateEvents(ROOM_ID);
    assertThat(response).isNotNull();
    assertThat(response).hasSize(4);
  }

  @Test
  void getStateEventsOverride_WithACorrectPaYload_thenReturnAListOfClientEvent() {
    final String EVENT_TYPE = "EVENT_TYPE";
    final String STATE_KEY = "STATE_KEY";
    stubFor(
        get(urlPathEqualTo(
                "/_matrix/client/v3/rooms/" + ROOM_ID + "/state/" + EVENT_TYPE + "/" + STATE_KEY))
            .withQueryParam("format", equalTo("event"))
            .willReturn(
                okJson(
                    """
                        {
                          "type": "m.room.name",
                          "event_id": "$143273582443PhrSn:example.org",
                          "sender": "@alice:example.org",
                          "origin_server_ts": 1432735824653,
                          "room_id": "!636q39766251:example.org",
                          "state_key": "",
                          "content": {
                            "name": "My Cool Room"
                          },
                          "unsigned": {
                            "age": 1234,
                            "prev_content": {
                              "name": "Old Room Name"
                            },
                            "replaces_state": "$1234prev:example.org"
                          }
                        }
                        """)));
    var response = client.events().getStateEvent(ROOM_ID, EVENT_TYPE, STATE_KEY);
    assertThat(response).isNotNull();
    assertThat(response.eventId()).isEqualTo(EventID.create("$143273582443PhrSn:example.org"));
    assertThat(response.sender()).isEqualTo(UserID.create("@alice:example.org"));
    assertThat(response.unsigned().age()).isEqualTo(1234);
  }

  @Test
  void getMessages_WithValidQueryParameters_thenReturnMessagesResponse() {
    EventID expectedChunkEventId = EventID.create("$abcdefg12345:matrix.org");

    QueryParametersMessages mockParams =
        new QueryParametersMessages("some_start_token", 20, "some_end_token");
    ChronologicalDirection direction =
        ChronologicalDirection.CHRONOLOGICAL_ORDER; // Adjust to your enum
    // name if needed

    stubFor(
        get(urlPathEqualTo("/_matrix/client/v3/rooms/" + ROOM_ID + "/messages"))
            .withQueryParam("dir", equalTo("f"))
            .withQueryParam("from", equalTo("some_start_token"))
            .withQueryParam("limit", equalTo("20"))
            .withQueryParam("to", equalTo("some_end_token"))
            .willReturn(
                okJson(
                    """
                        {
                          "start": "some_start_token",
                          "end": "another_end_token",
                          "chunk": [
                            {
                              "event_id": "%s",
                              "type": "m.room.message",
                              "sender": "@test:matrix.org",
                              "content": { "msgtype": "m.text", "body": "Hello timeline!" }
                            }
                          ]
                        }
                        """
                        .formatted(expectedChunkEventId))));

    Messages actualResponse = client.events().getMessages(ROOM_ID, direction, mockParams);

    assertNotNull(actualResponse, "The returned MessagesResponse payload shouldn't be null");
    assertEquals(
        "some_start_token", actualResponse.start(), "The start pagination token should match");
    assertEquals(
        "another_end_token", actualResponse.end(), "The end pagination token should match");
    assertFalse(
        actualResponse.chunk().isEmpty(), "The chunked event stream list should contain events");

    // Ensure serialization / list indexing works correctly downstream
    var firstEventId = actualResponse.chunk().getFirst().eventId();
    assertEquals(
        expectedChunkEventId,
        firstEventId,
        "The mapped chunk payload did not match the expected event " + "structure");
  }

  @Test
  void getEventCLosestToTimestamp_WithACorrectPayload_ThenReturnAnEventMetadata() {
    ChronologicalDirection chronologicalDirection = ChronologicalDirection.CHRONOLOGICAL_ORDER;
    int randomUnixDate = Math.abs(new Random().nextInt());
    long originServerTs = 1432735824653L;
    stubFor(
        get(urlPathEqualTo("/_matrix/client/v3/rooms/" + ROOM_ID + "/timestamp_to_event"))
            .withQueryParam("dir", equalTo(chronologicalDirection.getValue()))
            .withQueryParam("ts", equalTo(String.valueOf(randomUnixDate)))
            .willReturn(
                okJson(
                    """
                        {
                          "event_id": "$143273582443PhrSn:example.org",
                          "origin_server_ts": %d
                        }
                        """
                        .formatted(originServerTs))));
    var response =
        client.events().getEventClosestToTimestamp(ROOM_ID, chronologicalDirection, randomUnixDate);
    assertThat(response).isNotNull();
    assertThat(response.originServerTs()).isEqualTo(originServerTs);
  }

  @Test
  void getInitialSync_WithACorrectPayload_ThenReturnRoomInfo() {
    stubFor(
        get("/_matrix/client/v3/rooms/" + ROOM_ID + "/initialSync")
            .willReturn(
                okJson(
                    """
                        {
                          "account_data": [
                            {
                              "content": {
                                "tags": {
                                  "work": {
                                    "order": "1"
                                  }
                                }
                              },
                              "type": "m.tag"
                            }
                          ],
                          "membership": "join",
                          "messages": {
                            "chunk": [
                              {
                                "content": {
                                  "body": "This is an example text message",
                                  "format": "org.matrix.custom.html",
                                  "formatted_body": "<b>This is an example text message</b>",
                                  "msgtype": "m.text"
                                },
                                "event_id": "$143273582443PhrSn:example.org",
                                "origin_server_ts": 1432735824653,
                                "room_id": "!636q39766251:example.com",
                                "sender": "@example:example.org",
                                "type": "m.room.message",
                                "unsigned": {
                                  "age": 1234,
                                  "membership": "join"
                                }
                              },
                              {
                                "content": {
                                  "body": "something-important.doc",
                                  "filename": "something-important.doc",
                                  "info": {
                                    "mimetype": "application/msword",
                                    "size": 46144
                                  },
                                  "msgtype": "m.file",
                                  "url": "mxc://example.org/FHyPlCeYUSFFxlgbQYZmoEoe"
                                },
                                "event_id": "$143273582443PhrSn:example.org",
                                "origin_server_ts": 1432735824653,
                                "room_id": "!636q39766251:example.com",
                                "sender": "@example:example.org",
                                "type": "m.room.message",
                                "unsigned": {
                                  "age": 1234,
                                  "membership": "join"
                                }
                              }
                            ],
                            "end": "s3456_9_0",
                            "start": "t44-3453_9_0"
                          },
                          "room_id": "!636q39766251:example.com",
                          "state": [
                            {
                              "content": {
                                "join_rule": "public"
                              },
                              "event_id": "$143273582443PhrSn:example.org",
                              "origin_server_ts": 1432735824653,
                              "room_id": "!636q39766251:example.com",
                              "sender": "@example:example.org",
                              "state_key": "",
                              "type": "m.room.join_rules",
                              "unsigned": {
                                "age": 1234,
                                "membership": "join"
                              }
                            },
                            {
                              "content": {
                                "avatar_url": "mxc://example.org/SEsfnsuifSDFSSEF",
                                "displayname": "Alice Margatroid",
                                "membership": "join",
                                "reason": "Looking for support"
                              },
                              "event_id": "$143273582443PhrSn:example.org",
                              "origin_server_ts": 1432735824653,
                              "room_id": "!636q39766251:example.com",
                              "sender": "@alice:example.org",
                              "state_key": "@alice:example.org",
                              "type": "m.room.member",
                              "unsigned": {
                                "age": 1234,
                                "membership": "join"
                              }
                            },
                            {
                              "content": {
                                "m.federate": true,
                                "predecessor": {
                                  "event_id": "$something:example.org",
                                  "room_id": "!oldroom:example.org"
                                },
                                "room_version": "11"
                              },
                              "event_id": "$143273582443PhrSn:example.org",
                              "origin_server_ts": 1432735824653,
                              "room_id": "!636q39766251:example.com",
                              "sender": "@example:example.org",
                              "state_key": "",
                              "type": "m.room.create",
                              "unsigned": {
                                "age": 1234,
                                "membership": "join"
                              }
                            },
                            {
                              "content": {
                                "ban": 50,
                                "events": {
                                  "m.room.name": 100,
                                  "m.room.power_levels": 100
                                },
                                "events_default": 0,
                                "invite": 50,
                                "kick": 50,
                                "notifications": {
                                  "room": 20
                                },
                                "redact": 50,
                                "state_default": 50,
                                "users": {
                                  "@example:localhost": 100
                                },
                                "users_default": 0
                              },
                              "event_id": "$143273582443PhrSn:example.org",
                              "origin_server_ts": 1432735824653,
                              "room_id": "!636q39766251:example.com",
                              "sender": "@example:example.org",
                              "state_key": "",
                              "type": "m.room.power_levels",
                              "unsigned": {
                                "age": 1234,
                                "membership": "join"
                              }
                            }
                          ],
                          "visibility": "private"
                        }
                        """)));
    var response = client.events().getInitialSync(ROOM_ID);
    assertThat(response).isNotNull();
  }

  private static Stream<Arguments> provideEventsForSendStateEvent() {
    return Stream.of(
        Arguments.of(
            new RoomAvatar(
                new RoomAvatar.AvatarInfo(
                    480,
                    "image/png",
                    102400,
                    new ThumbnailInfo(150, 150, 8192, "image/png"),
                    URI.create("mxc://example.org/thumbnailavataruri"),
                    480),
                URI.create("mxc://example.org/avataruri")),
            ""),
        Arguments.of(new RoomCanonicalAlias("#room:example.org", List.of("#alt:example.org")), ""),
        Arguments.of(
            new RoomCreate(
                List.of("@alice:example.org"),
                true,
                new RoomCreate.PreviousRoom(
                    EventID.create("$oldevent:example.org"), RoomID.create("!oldroom:example.org")),
                "13",
                "m.space"),
            ""),
        Arguments.of(new RoomEncryption("m.megolm.v1.aes-sha2", 10, 10), ""),
        Arguments.of(new RoomGuestAccess(GuestAccessType.CAN_JOIN), ""),
        Arguments.of(new RoomHistoryVisibility(HistoryVisibilityType.SHARED), ""),
        Arguments.of(
            new RoomJoinRules(
                List.of(new RoomJoinRules.AllowCondition(RoomID.create("!spaceroom:example.org"))),
                "restricted"),
            ""),
        Arguments.of(
            new RoomMember(
                URI.create("mxc://example.org/avataruri"),
                "Alice",
                Boolean.TRUE,
                null,
                Membership.JOIN,
                null,
                new ThirdPartyInvite(
                    "displayName",
                    new ThirdPartyInvite.SignedThirdPartyInvite(
                        UserID.create("@user:example.org"),
                        Map.ofEntries(
                            Map.entry(
                                "magic.forest",
                                Map.ofEntries(Map.entry("ed25519:0", "SomeSignatureBase64Here")))),
                        "token"))),
            "@alice:example.org"),
        Arguments.of(new RoomName("Test Room"), ""),
        Arguments.of(new RoomPinnedEvents(List.of("$event1:example.org")), ""),
        Arguments.of(
            new RoomPowerLevels(
                50,
                Map.of("m.room.name", 50, "m.room.power_levels", 100),
                0,
                0,
                50,
                RoomPowerLevels.Notifications.of(Map.of("room", 50)),
                50,
                0,
                Map.of(UserID.create("@alice:example.org"), 100),
                0),
            ""),
        Arguments.of(
            new RoomTopic(
                new RoomTopic.TopicContentBlock(
                    List.of(
                        new RoomTopic.TopicContentBlock.TextualRepresentation(
                            "Test topic", "text/plain"))),
                "text/plain"),
            ""),
        Arguments.of(new RoomServerAcl(List.of("*"), true, List.of()), ""),
        Arguments.of(
            new RoomThirdPartyInvite(
                "Alice",
                URI.create("https://identity.example.org/_matrix/identity/v2/pubkey/isvalid"),
                "publicKeyBase64Here",
                List.of(
                    new RoomThirdPartyInvite.PublicKeys(
                        "https://identity.example.org/_matrix/identity/v2/pubkey/isvalid",
                        "anotherPublicKeyBase64Here"))),
            "token123"),
        Arguments.of(new RoomTombstone("Upgraded", "!newroom:example.org"), ""),
        Arguments.of(
            new SpaceChild("lexicographically_compare_me", false, List.of("example.org")),
            "!child:example.org"),
        Arguments.of(new SpaceParent(true, List.of("example.org")), "!parent:example.org"));
  }

  @ParameterizedTest(name = "[{index}]: {0}")
  @MethodSource("provideEventsForSendStateEvent")
  void sendStateEvent_WithACorrectPayload_ThenReturnAString(
      StateEventContent stateEvent, String stateKey) {
    String eventId = "$abc123def456:example.org";
    String eventType = EventService.resolveStateWireType(stateEvent);

    String expectedPath =
        "/_matrix/client/v3/rooms/" + ROOM_ID + "/state/" + eventType + "/" + stateKey;

    var expectedBody = Mapper.writeValueAsBytes(stateEvent);

    stubFor(
        put(urlEqualTo(expectedPath))
            .withRequestBody(equalToJson(new String(expectedBody), true, true))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"event_id\":\"" + eventId + "\"}")));

    var response = client.events().sendStateEvent(ROOM_ID, stateKey, stateEvent);

    assertThat(response).isEqualTo(eventId);
  }

  private static Stream<Arguments> provideEventsForSendMessageEvent() {
    String callId = "c9a281f6-52e3-4a3b-9e21-8f2b3c4d5e6f";
    UserID userId = UserID.create("@alice:matrix.org");
    String partyId = "WEBRTC-7f3a9c";
    String otherPartyId = "WEBRTC-1b2c3d";
    int lifetime = 30000;
    String version = "1";

    // Required by spec to carry \r\n
    String sdpOffer =
        """
                        v=0\r
                        o=- 6398247103928471 2 IN IP4 127.0.0.1\r
                        s=-\r
                        t=0 0\r
                        a=group:BUNDLE 0 1\r
                        m=audio 9 UDP/TLS/RTP/SAVPF 111\r
                        c=IN IP4 0.0.0.0\r
                        a=mid:0\r
                        a=sendrecv\r
                        """;

    String sdpAnswer =
        """
                        v=0\r
                        o=- 9182736450192837 2 IN IP4 127.0.0.1\r
                        s=-\r
                        t=0 0\r
                        a=group:BUNDLE 0 1\r
                        m=audio 9 UDP/TLS/RTP/SAVPF 111\r
                        c=IN IP4 0.0.0.0\r
                        a=mid:0\r
                        a=recvonly\r
                        """;

    Map<String, StreamMetadata> streamMetadata =
        Map.of("stream_1", new StreamMetadata(false, PurposeType.SCREEN_SHARE, false));

    return Stream.of(
        Arguments.of(
            new TextContent(
                "Hey, are we still on for the standup at 10?",
                "org.matrix.custom.html",
                "<p>Hey, are we still on for the standup at 10?</p>")),
        Arguments.of(
            new Sticker(
                "Waving hello",
                new ImageInfo(
                    480,
                    320,
                    45_000,
                    "image/png",
                    false,
                    null,
                    null,
                    URI.create("mxc://matrix.org/qWeRtYuIoP123")),
                URI.create("mxc://matrix.org/qWeRtYuIoP123"))),
        Arguments.of(
            new Reaction(
                new Reaction.ReactionRelatesTo(
                    EventID.create("$oNGL5s3dNAdCLjkiHZLR4YOhV1kbLbAWlbYFVLnu6dc"),
                    "\uD83D\uDE00"))), // 😀
        Arguments.of(
            new RoomRedaction(
                "Contained a leaked API key",
                EventID.create("$RcJj6bYqOhFq3Kx9v7pQdW2mLzT8hNsXeYjA1bVfCwI"))),
        Arguments.of(
            new RoomEncrypted(
                "m.megolm.v1.aes-sha2",
                new Ciphertext.Megolm(
                    "AwgAEnACgAYMGii7ScejxUbFozjWvOJEDeMDVQp2loxjJUwn5aVwB5fVh40W9jyGKw"),
                "X3lUlvLELLYxeTx4yOVu6UDpasGEVO2QYm4qN8UtKA0")),
        Arguments.of(
            new CallInvite(
                callId,
                partyId,
                version,
                userId,
                lifetime,
                new CallInvite.Offer(sdpOffer),
                streamMetadata),
            30000L),
        Arguments.of(
            new CallCandidates(
                callId,
                partyId,
                version,
                List.of(
                    new CallCandidates.Candidate(
                        "candidate:842163049 1 udp 1677729535 203.0.113.5 54609 typ srflx raddr 192.168.1.10 rport 54609",
                        0,
                        "0")))),
        Arguments.of(
            new CallAnswer(
                callId, partyId, version, new CallAnswer.Answer(sdpAnswer), streamMetadata)),
        Arguments.of(new CallSelectAnswer(partyId, callId, version, otherPartyId)),
        Arguments.of(
            new CallNegotiate(
                callId,
                partyId,
                version,
                new CallNegotiate.Description(sdpOffer, CallSessionDescriptorType.OFFER),
                lifetime,
                streamMetadata)),
        Arguments.of(new CallReject(callId, partyId, version)),
        Arguments.of(new CallHangup(callId, partyId, version, ReasonType.USER_HANGUP)),
        Arguments.of(
            new KeyVerificationRequest(
                "DEVICEID789JKL", List.of("m.sas.v1"), 1_755_000_000_000L, "transaction-8f3a9c")),
        Arguments.of(
            new KeyVerificationStart(
                "DEVICEID789JKL",
                new VerificationRelatesTo(EventID.create("$mYcVerificationRequestEventID12345")),
                "m.sas.v1",
                null,
                "transaction-8f3a9c")),
        Arguments.of(
            new KeyVerificationAccept(
                "Yw2fjfz9pQ8dR1kLmN0vB3xC6eF7gH4iJ5kL9mN2oP1q",
                "sha256",
                "curve25519-hkdf-sha256",
                new VerificationRelatesTo(EventID.create("$mYcVerificationStartEventID67890")),
                "hkdf-hmac-sha256",
                List.of("decimal", "emoji"),
                "transaction-8f3a9c")),
        Arguments.of(
            new KeyVerificationMac(
                "ed25519:DEVICEID789JKL",
                new VerificationRelatesTo(EventID.create("$mYcVerificationAcceptEventID54321")),
                Map.of("ed25519:DEVICEID789JKL", "3s5f7Vn8xQpLzT2mWjR6oKcE9dY1bAuF4hJgN0iX7wI"),
                "transaction-8f3a9c")),
        Arguments.of(
            new KeyVerificationDone(
                new VerificationRelatesTo(EventID.create("$mYcVerificationMacEventID11223")),
                "transaction-8f3a9c")),
        Arguments.of(
            new KeyVerificationCancel(
                CancelCode.Known.USER,
                new VerificationRelatesTo(EventID.create("$mYcVerificationStartEventID67890")),
                "User cancelled the verification.",
                "transaction-8f3a9c")));
  }

  @ParameterizedTest(name = "[{index}]: {0}")
  @MethodSource("provideEventsForSendMessageEvent")
  void sendMessageEvent_WithACorrectPayload_thenReturnAString(MessageEventContent messageEvent) {
    String roomMessageType = "m.room.message";
    String expectedEventId = "$h29asdf8q348hju9a:matrix.org";

    stubFor(
        put(urlPathMatching(
                "/_matrix/client/v3/rooms/" + ROOM_ID + "/send/" + roomMessageType + "/[^/]+"))
            .withRequestBody(
                equalToJson(
                    """
                        {
                            "body": "Hello World",
                            "msgtype": "m.text"
                        }
                        """,
                    true,
                    true))
            .willReturn(
                okJson(
                    """
                        {"event_id": "%s"}
                        """
                        .formatted(expectedEventId))));

    RoomMessage textEvent = new TextContent("Hello World", null, null);

    var actualEventId =
        client.events().sendMessageEvent(ROOM_ID, String.valueOf(UUID.randomUUID()), textEvent);

    assertNotNull(actualEventId, "The returned event ID should not be null");
    assertEquals(expectedEventId, actualEventId, "The client did not return the expected event ID");
  }

  @Test
  void sendRedactEvent_WithACorrectPayload_ThenReturnAString() {
    EventID eventId = EventID.create("$mYcVerificationRequestEventID12345");
    String txnID = UUID.randomUUID().toString();
    stubFor(
        put(urlEqualTo("/_matrix/client/v3/rooms/" + ROOM_ID + "/redact/" + eventId + "/" + txnID))
            .withRequestBody(
                equalToJson(
                    """
                            {
                            "reason": "Reason"
                            }"""))
            .willReturn(
                okJson(
                    """
                        {
                          "event_id": "$YUwQidLecu:example.com"
                        }""")));

    var response = client.events().redactEvent(ROOM_ID, eventId, txnID, "Reason");
    assertThat(response).isNotNull();
  }

  @Test
  void sendPublishRoomMessageFile_WithACorrectPayload_thenReturnAString(@TempDir Path tempDir)
      throws IOException {
    Result result = getResult(tempDir);

    // Mock the MXC Request (v1 create endpoint)
    stubFor(
        post(urlEqualTo("/_matrix/media/v1/create"))
            .willReturn(okJson("{\"content_uri\": \"" + result.mockMxcUri() + "\"}")));

    // Mock the File Upload (v3 upload endpoint with filename query param)
    stubFor(
        put(urlEqualTo(
                "/_matrix/media/v3/upload/"
                    + result.serverName()
                    + "/"
                    + result.mediaId()
                    + "?filename=file.txt"))
            .withRequestBody(containing("Test"))
            .willReturn(ok()));

    // Mock the Message Publication (v3 client send timeline endpoint)
    stubFor(
        put(urlPathMatching(
                "/_matrix/client/v3/rooms/"
                    + result.roomId()
                    + "/send/"
                    + result.roomMessageType()
                    + "/[^/]+"))
            .withRequestBody(containing(String.valueOf(result.mockMxcUri)))
            .withRequestBody(containing("file.txt"))
            .willReturn(okJson("{\"event_id\": \"" + result.expectedEventId() + "\"}")));

    var mxc = client.events().uploadResource(result.tempFile);

    FileContent file =
        new FileContent(
            "Test caption", null, result.tempFile.toString(), null, null, null, URI.create(mxc));
    var actualEventId =
        client.events().sendMessageEvent(result.roomId(), String.valueOf(UUID.randomUUID()), file);

    assertNotNull(actualEventId, "The returned event ID should not be null");
    assertEquals(
        result.expectedEventId(), actualEventId, "The client did not return the expected event ID");
  }

  @Test
  void sendPublishRoomMessageFile_WithACorrectPayload_thenReturnAnException(@TempDir Path tempDir)
      throws IOException {
    Result result = getResult(tempDir);

    stubFor(
        post(urlEqualTo("/_matrix/media/v1/create"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ malformed json : [")));

    assertThatThrownBy(() -> client.events().uploadResource(result.tempFile))
        .isInstanceOf(MatrixSerializationException.class);
  }

  @Test
  void getSync_WithValidQueryParameters_thenReturnSyncResponse() {
    String joinedRoomId = "!726s6s6q:example.com";
    String invitedRoomId = "!696r7674:example.com";
    String knockedRoomId = "!223asd456:example.com";
    String leftRoomId = "!left12345:example.com";
    EventID expectedChunkEventId = EventID.create("$143273582443PhrSn:example.org");
    String expectedNextBatch = "s72595_4483_1934";

    stubFor(
        get(urlPathEqualTo("/_matrix/client/v3/sync"))
            .willReturn(
                okJson(
                    """
                        {
                          "account_data": {
                            "events": [
                              {
                                "content": {
                                  "custom_config_key": "custom_config_value"
                                },
                                "type": "org.example.custom.config"
                              }
                            ]
                          },
                          "next_batch": "s72595_4483_1934",
                          "device_lists": {
                            "changed": ["@alice:matrix.org"],
                            "left": []
                          },
                          "to_device": {
                            "events": [
                              {
                                "content": {
                                  "example_content_key": "value"
                                },
                                "sender": "@alice:example.com",
                                "type": "m.new_device"
                              }
                            ]
                          },
                          "presence": {
                            "events": [
                              {
                                "content": {
                                  "avatar_url": "mxc://localhost/wefuiwegh8742w",
                                  "currently_active": false,
                                  "last_active_ago": 2478593,
                                  "presence": "online",
                                  "status_msg": "Making cupcakes"
                                },
                                "sender": "@example:localhost",
                                "type": "m.presence"
                              }
                            ]
                          },
                          "rooms": {
                            "invite": {
                              "!696r7674:example.com": {
                                "invite_state": {
                                  "events": [
                                    {
                                      "content": {
                                        "name": "My Room Name"
                                      },
                                      "sender": "@alice:example.com",
                                      "state_key": "",
                                      "type": "m.room.name"
                                    },
                                    {
                                      "content": {
                                        "membership": "invite"
                                      },
                                      "sender": "@alice:example.com",
                                      "state_key": "@bob:example.com",
                                      "type": "m.room.member"
                                    }
                                  ]
                                }
                              }
                            },
                            "join": {
                              "!726s6s6q:example.com": {
                                "account_data": {
                                  "events": [
                                    {
                                      "content": {
                                        "tags": {
                                          "u.work": {
                                            "order": 0.9
                                          }
                                        }
                                      },
                                      "type": "m.tag"
                                    },
                                    {
                                      "content": {
                                        "custom_config_key": "custom_config_value"
                                      },
                                      "type": "org.example.custom.room.config"
                                    }
                                  ]
                                },
                                "ephemeral": {
                                  "events": [
                                    {
                                      "content": {
                                        "user_ids": [
                                          "@alice:matrix.org",
                                          "@bob:example.com"
                                        ]
                                      },
                                      "type": "m.typing"
                                    },
                                    {
                                      "content": {
                                        "$1435641916114394fHBLK:matrix.org": {
                                          "m.read": {
                                            "@erikj:jki.re": {
                                              "ts": 1436451550453
                                            }
                                          },
                                          "m.read.private": {
                                            "@self:example.org": {
                                              "ts": 1661384801651
                                            }
                                          }
                                        }
                                      },
                                      "type": "m.receipt"
                                    }
                                  ]
                                },
                                "state": {
                                  "events": [
                                    {
                                      "content": {
                                        "avatar_url": "mxc://example.org/SFHyPlCeYUSFFxlgbQYZmoEoe",
                                        "displayname": "Example user",
                                        "membership": "join"
                                      },
                                      "event_id": "$143273976499sgjks:example.org",
                                      "origin_server_ts": 1432735824653,
                                      "sender": "@example:example.org",
                                      "state_key": "@example:example.org",
                                      "type": "m.room.member",
                                      "unsigned": {
                                        "age": 45603,
                                        "membership": "join"
                                      }
                                    }
                                  ]
                                },
                                "summary": {
                                  "m.heroes": [
                                    "@alice:example.com",
                                    "@bob:example.com"
                                  ],
                                  "m.invited_member_count": 0,
                                  "m.joined_member_count": 2
                                },
                                "timeline": {
                                  "events": [
                                    {
                                      "content": {
                                        "avatar_url": "mxc://example.org/SEsfnsuifSDFSSEF",
                                        "displayname": "Alice Margatroid",
                                        "membership": "join",
                                        "reason": "Looking for support"
                                      },
                                      "event_id": "$143273582443PhrSn:example.org",
                                      "origin_server_ts": 1432735824653,
                                      "sender": "@alice:example.org",
                                      "state_key": "@alice:example.org",
                                      "type": "m.room.member",
                                      "unsigned": {
                                        "age": 1234,
                                        "membership": "join"
                                      }
                                    },
                                    {
                                      "content": {
                                        "body": "This is an example text message",
                                        "format": "org.matrix.custom.html",
                                        "formatted_body": "<b>This is an example text message</b>",
                                        "msgtype": "m.text"
                                      },
                                      "event_id": "$143273582443PhrSn2:example.org",
                                      "origin_server_ts": 1432735824653,
                                      "sender": "@example:example.org",
                                      "type": "m.room.message",
                                      "unsigned": {
                                        "age": 1234,
                                        "membership": "join"
                                      }
                                    }
                                  ],
                                  "limited": true,
                                  "prev_batch": "t34-23535_0_0"
                                },
                                "unread_notifications": {
                                  "highlight_count": 1,
                                  "notification_count": 5
                                },
                                "unread_thread_notifications": {
                                  "$threadroot": {
                                    "highlight_count": 3,
                                    "notification_count": 6
                                  }
                                }
                              }
                            },
                            "knock": {
                              "!223asd456:example.com": {
                                "knock_state": {
                                  "events": [
                                    {
                                      "content": {
                                        "name": "My Room Name"
                                      },
                                      "sender": "@alice:example.com",
                                      "state_key": "",
                                      "type": "m.room.name"
                                    },
                                    {
                                      "content": {
                                        "membership": "knock"
                                      },
                                      "sender": "@bob:example.com",
                                      "state_key": "@bob:example.com",
                                      "type": "m.room.member"
                                    }
                                  ]
                                }
                              }
                            },
                            "leave": {
                              "!left12345:example.com": {
                                "account_data": { "events": [] },
                                "state": { "events": [] },
                                "timeline": { "events": [], "limited": false, "prev_batch": "t00-00000_0_0" }
                              }
                            }
                          }
                        }
                        """)));

    Sync actualResponse =
        client.events().sync(new QueryParametersSync(null, true, null, null, null, null));

    assertNotNull(actualResponse, "The returned SyncResponse payload shouldn't be null");
    assertEquals(
        expectedNextBatch, actualResponse.nextBatch(), "The next_batch token should match");

    // rooms.join
    assertTrue(
        actualResponse.rooms().join().containsKey(joinedRoomId),
        "Joined rooms should contain the test room");
    var joinedRoom = actualResponse.rooms().join().get(joinedRoomId);
    assertFalse(
        joinedRoom.timeline().events().isEmpty(), "Joined room timeline should contain events");
    assertEquals(
        expectedChunkEventId,
        joinedRoom.timeline().events().getFirst().eventId(),
        "The mapped timeline event did not match the expected event structure");
    assertEquals(2, joinedRoom.summary().mJoinedMemberCount(), "Joined member count should match");
    assertEquals(
        1, joinedRoom.unreadNotifications().highlightCount(), "Highlight count should match");

    // rooms.invite
    assertTrue(
        actualResponse.rooms().invite().containsKey(invitedRoomId),
        "Invited rooms should contain the test room");
    assertFalse(
        actualResponse.rooms().invite().get(invitedRoomId).inviteState().events().isEmpty(),
        "Invite state should contain stripped state events");

    // rooms.knock
    assertTrue(
        actualResponse.rooms().knock().containsKey(knockedRoomId),
        "Knocked rooms should contain the test room");
    assertFalse(
        actualResponse.rooms().knock().get(knockedRoomId).knockState().events().isEmpty(),
        "Knock state should contain stripped state events");

    // rooms.leave
    assertTrue(
        actualResponse.rooms().leave().containsKey(leftRoomId),
        "Left rooms should contain the test room");

    // top-level fields
    assertFalse(
        actualResponse.accountData().events().isEmpty(), "Account data events should be present");
    assertFalse(actualResponse.presence().events().isEmpty(), "Presence events should be present");
    assertEquals(
        List.of("@alice:matrix.org"),
        actualResponse.deviceLists().changed(),
        "Device list changed should match");
    assertFalse(actualResponse.toDevice().events().isEmpty(), "To-device events should be present");
  }

  private record Result(
      RoomID roomId,
      String roomMessageType,
      String expectedEventId,
      String serverName,
      String mediaId,
      URI mockMxcUri,
      Path tempFile) {}
}
