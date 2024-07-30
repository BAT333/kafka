package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraud = new FraudDetectorService();

try ( var kafka = new KafkaService<>("ECOMMERCE_NEW_ORDER_4", fraud::parse, FraudDetectorService.class.getSimpleName(),Order.class, Map.of())){
    kafka.run();
}
    }
    private void parse(ConsumerRecord<String,Order> record){
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }





}
