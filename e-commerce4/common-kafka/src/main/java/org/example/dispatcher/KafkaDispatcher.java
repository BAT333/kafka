package org.example.dispatcher;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.CorrelationId;
import org.example.Message;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaDispatcher<T> implements Closeable {
    private final KafkaProducer<String, Message<T>> producer;


    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());


    }


    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG,"all");
        return properties;
    }
    public void send(String topic, String key, T peyLoad, CorrelationId correId) throws ExecutionException, InterruptedException {
        var future = this.sendAsync(topic,key,peyLoad,correId);
        future.get();
    }
    public Future<RecordMetadata> sendAsync(String topic, String key, T peyLoad, CorrelationId correId) throws ExecutionException, InterruptedException {
        var value = new Message<T>(peyLoad,correId.continueWith("_"+ topic));
        var record =new ProducerRecord<>(topic,key,value);
        Callback callback =(data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Sent with success " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };
      return producer.send(record,callback);

    }

    @Override
    public void close(){
        this.producer.close();
    }
}
