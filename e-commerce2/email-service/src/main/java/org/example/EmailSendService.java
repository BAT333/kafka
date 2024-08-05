package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailSendService {
    public static void main(String[] args) throws Exception {
        var emailService = new EmailSendService();
      try( var service = new KafkaService<>("ECOMMERCE_SEND_EMAIL_1", emailService::parse,EmailSendService.class.getSimpleName(), Email.class, Map.of())) {
          service.run();
      }
    }
    private void parse(ConsumerRecord<String,Email> record){
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

    }

}
