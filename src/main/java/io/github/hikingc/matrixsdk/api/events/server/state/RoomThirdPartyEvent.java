package io.github.hikingc.matrixsdk.api.events.server.state;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.hikingc.matrixsdk.api.events.StateEvent;
import io.github.hikingc.matrixsdk.api.events.UnsignedData;
import io.github.hikingc.matrixsdk.api.events.matrix.room.ThirdPartyInvite;

@JsonTypeName("m.room.third_party_invite")
public record RoomThirdPartyEvent(
    ThirdPartyInvite content,
    String eventId,
    Long originServerTs,
    String roomId,
    String sender,
    String stateKey,
    String type,
    UnsignedData unsigned)
    implements StateEvent<ThirdPartyInvite> {}
