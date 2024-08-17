package org.example.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.Message;
import org.example.MessageAdapter;

public class GsonDeserializer implements Deserializer<Message> {
    private final Gson gson= new GsonBuilder().registerTypeAdapter(Message.class,new MessageAdapter()).create();

    @Override
    public Message deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes),Message.class);
    }
}









    /*private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName =String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type =(Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

 */