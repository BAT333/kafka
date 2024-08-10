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
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String,Message<T>> consumer;
    private final ConsumerFunction<T> parse;


    private KafkaService(ConsumerFunction<T> parse, String simpleName,Map<String,String> configPro){
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(simpleName,configPro));
    }


    public KafkaService(String topic, ConsumerFunction<T> parse, String simpleName, Map<String,String> configPro) throws Exception {
        this(parse,simpleName,configPro);
        this.consumer.subscribe(Collections.singletonList(topic));
        this.run();
    }
    public KafkaService(Pattern topic, ConsumerFunction<T> parse, String simpleName, Map<String,String> configPro) throws Exception {
        this(parse,simpleName,configPro);
        this.consumer.subscribe(topic);
        this.run();
    }



    public void run() throws ExecutionException, InterruptedException {
        try(var dispatcher =new KafkaDispatcher<>()) {
            while (true) {
                var records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    System.out.println("Encontrei " + records.count() + " registros");
                    for (var record : records) {
                        try {
                            parse.consume(record);
                        } catch (Exception e) {
                            var mensagem = record.value();
                            dispatcher.send("ECOMMERCE_DEADLETTER", mensagem.getCorrelationId().toString(), new GsonSerializer().serialize("", mensagem), mensagem.getCorrelationId().continueWith("deadLetter"));
                        }
                    }
                }
            }
        }
    }
    private  Properties properties( String simpleName, Map<String, String> configPro) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, simpleName);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG,UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"1");
        properties.putAll(configPro);
        return properties;
    }

    @Override
    public void close(){
        this.consumer.close();
    }
}
