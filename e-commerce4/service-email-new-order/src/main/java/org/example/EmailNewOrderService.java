package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.consumer.KafkaService;
import org.example.dispatcher.KafkaDispatcher;


import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class EmailNewOrderService {
    public static void main(String[] args) {
        var emailService = new EmailNewOrderService();

        try ( var kafka = new KafkaService<>("ECOMMERCE_NEW_EMAIL", emailService::parse, EmailNewOrderService.class.getSimpleName(), Map.of())){
            kafka.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private final KafkaDispatcher<String> dispatcher = new KafkaDispatcher<>();
    private void parse(ConsumerRecord<String,Message<Order>> record) {
        try {
            dispatcher.send("ECOMMERCE_SEND_EMAIL_1",record.value().getPayLond().getEmail(),"OLA",record.value().getCorrelationId().continueWith(EmailNewOrderService.class.getSimpleName()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
