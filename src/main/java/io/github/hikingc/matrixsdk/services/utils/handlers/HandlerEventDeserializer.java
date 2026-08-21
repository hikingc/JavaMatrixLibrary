package io.github.hikingc.matrixsdk.services.utils.handlers;

import io.github.hikingc.matrixsdk.api.events.ClientEvent;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.type.TypeFactory;

/// Utility class meant to handle and notify about bad JSON payloads.
///
/// @apiNote WIP class.
public final class HandlerEventDeserializer extends StdDeserializer<List<ClientEvent<?>>> {

  private static final Logger log = LoggerFactory.getLogger(HandlerEventDeserializer.class);

  public HandlerEventDeserializer() {
    super(
        TypeFactory.createDefaultInstance().constructCollectionType(List.class, ClientEvent.class));
  }

  @Override
  public List<ClientEvent<?>> deserialize(JsonParser p, DeserializationContext ctx) {
    List<ClientEvent<?>> result = new ArrayList<>();
    if (p.currentToken() != JsonToken.START_ARRAY) {
      return ctx.reportInputMismatch(ClientEvent.class, "Expected START_ARRAY for event list");
    }
    while (p.nextToken() != JsonToken.END_ARRAY) {
      JsonNode node = ctx.readTree(p);
      try {
        ClientEvent<?> event = ctx.readTreeAsValue(node, ClientEvent.class);
        result.add(event);
      } catch (JacksonException e) {
        log.warn(
            "Skipping malformed event -> event_id={} type={} state_key={} sender={}: {}",
            node.path("event_id").asString("?"),
            node.path("type").asString("?"),
            node.path("state_key").asString("-"),
            node.path("sender").asString("?"),
            e.getMessage());
      }
    }
    return result;
  }
}
