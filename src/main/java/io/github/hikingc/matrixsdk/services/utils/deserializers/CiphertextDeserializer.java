package io.github.hikingc.matrixsdk.services.utils.deserializers;

import io.github.hikingc.matrixsdk.api.events.matrix.room.Ciphertext;
import io.github.hikingc.matrixsdk.api.events.matrix.room.CiphertextInfo;
import java.util.Map;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

public final class CiphertextDeserializer extends ValueDeserializer<Ciphertext> {
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
