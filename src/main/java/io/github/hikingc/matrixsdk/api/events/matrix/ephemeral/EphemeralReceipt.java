package io.github.hikingc.matrixsdk.api.events.matrix.ephemeral;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.hikingc.matrixsdk.api.events.matrix.EphemeralContent;
import io.github.hikingc.matrixsdk.api.identifiers.EventID;
import io.github.hikingc.matrixsdk.api.identifiers.UserID;
import java.util.Map;

public record EphemeralReceipt(@JsonValue Map<EventID, EventReceipts> receipts) implements EphemeralContent {
  public record EventReceipts(
      @JsonProperty("m.read") Map<UserID, Receipt> mRead,
      @JsonProperty("m.read.private") Map<UserID, Receipt> mReadPrivate) {}

  public record Receipt(String threadId, Integer ts) {}
}
