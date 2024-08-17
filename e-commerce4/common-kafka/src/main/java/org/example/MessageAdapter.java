package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {

    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type",message.getPayLond().getClass().getName());//add o tipo e propriedade tipo
        obj.add("payLoad", context.serialize(message.getPayLond()));//aqui serializa o payLond
        obj.add("correlationId", context.serialize(message.getCorrelationId()));//serializa o id
        return obj;
    }


    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        var obj = jsonElement.getAsJsonObject();
        var payLoandType = obj.get("type").getAsString();
        var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"),CorrelationId.class);
        try {
            var payLoad = context.deserialize(obj.get("payLoad"),Class.forName(payLoandType));
            return new Message(payLoad,correlationId);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }

    }
}
