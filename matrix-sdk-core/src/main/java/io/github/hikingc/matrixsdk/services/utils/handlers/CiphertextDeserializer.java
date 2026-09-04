package io.github.hikingc.matrixsdk.services.utils.handlers;

import io.github.hikingc.matrixsdk.api.events.matrix.room.Ciphertext;
import io.github.hikingc.matrixsdk.api.events.matrix.room.CiphertextInfo;
import java.util.Map;

import io.github.hikingc.matrixsdk.api.events.matrix.room.RoomEncrypted;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.type.TypeFactory;

/// Utility class with the intention of handling the Union type of [Ciphertext] field in
/// [RoomEncrypted] content payloads.
public final class CiphertextDeserializer extends StdDeserializer<Ciphertext> {
  public CiphertextDeserializer() {
    super(TypeFactory.createDefaultInstance().constructType(Ciphertext.class));
  }

  @Override
  public Ciphertext deserialize(JsonParser p, DeserializationContext ctx) {
    JsonNode node = ctx.readTree(p);

    if (node.isString()) {
      return new Ciphertext.Megolm(node.asString());
    }
    if (node.isObject()) {
      JavaType mapType =
          ctx.getTypeFactory().constructMapType(Map.class, String.class, CiphertextInfo.class);
      Map<String, CiphertextInfo> map = ctx.readTreeAsValue(node, mapType);
      return new Ciphertext.Olm(map);
    }
    return ctx.reportInputMismatch(Ciphertext.class, "Ciphertext must be a string or an object");
  }
}
