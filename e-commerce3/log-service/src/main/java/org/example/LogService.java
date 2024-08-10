package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService  {
    public static void main(String[] args) {
        var log = new LogService();
try(var consumer =new  KafkaService<String>(Pattern.compile("ECOMMERCE.*"),LogService::parse,LogService.class.getSimpleName(), Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
    consumer.run();
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    private static void parse(ConsumerRecord<String, Message<String>> record) {
        System.out.println("---------------------");
        System.out.println("LOG");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }
}
