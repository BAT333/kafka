package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction<T> parse;
    private final Class<T> type;

    private KafkaService(ConsumerFunction<T> parse, String simpleName, Class<T> type,Map<String,String> configPro){
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(type,simpleName,configPro));
        this.type = type;
    }


    public KafkaService(String topic, ConsumerFunction<T> parse, String simpleName, Class<T> type,Map<String,String> configPro) throws Exception {
        this(parse,simpleName,type,configPro);
        this.consumer.subscribe(Collections.singletonList(topic));
        this.run();
    }
    public KafkaService(Pattern topic, ConsumerFunction<T> parse, String simpleName, Class<T> type, Map<String,String> configPro) throws Exception {
        this(parse,simpleName,type,configPro);
        this.consumer.subscribe(topic);
        this.run();
    }



    public void run() throws Exception {
        while(true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }
    private  Properties properties(Class<T> type, String simpleName, Map<String, String> configPro) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, simpleName);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG,UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"1");
        properties.setProperty(GsonDeserializer.TYPE_CONFIG,type.getName());
        properties.putAll(configPro);
        return properties;
    }

    @Override
    public void close(){
        this.consumer.close();
    }
}
