package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.SQLException;
import java.util.Map;

public class CreaterUserService {

    public static void main(String[] args) {
        var createrUserService = new CreaterUserService();
        try(var kafka = new KafkaService<>("ECOMMERCE_NEW_ORDER_4", createrUserService::parse, CreaterUserService.class.getSimpleName(), Map.of())){
            kafka.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   private final UserService service = new UserService();
    private void parse(ConsumerRecord<String,Message<Order>> record) throws SQLException {
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        var order  = record.value().getPayLond();
        System.out.println(order);
        if(isNewUser(order.getEmail())){
           var user = service.save(new User (order.getEmail(),order.getOrderId()));
            System.out.println(user);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNewUser(String email) throws SQLException {
        return this.service.exists(email);
    }
}
