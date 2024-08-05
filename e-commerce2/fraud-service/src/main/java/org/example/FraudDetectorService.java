package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraud = new FraudDetectorService();

        try ( var kafka = new KafkaService<>("ECOMMERCE_NEW_ORDER_4", fraud::parse, FraudDetectorService.class.getSimpleName(),Order.class, Map.of())){
            kafka.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private final KafkaDispatcher<Order> dispatcher = new KafkaDispatcher<>();
    private void parse(ConsumerRecord<String,Order> record){
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var order =  record.value();
        if(this.isFraud(order.getAmount())){
            System.out.println("Order is a fraud");
            try {
                this.dispatcher.send("ECOMMERCE_NEW_ORDER_REJECTED",order.getOrderId(),order);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Approved: "+ order);
            try {
                this.dispatcher.send("ECOMMERCE_NEW_ORDER_APPROVED",order.getOrderId(),order);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isFraud(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("4500"))>=0;
    }


}
