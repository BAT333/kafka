package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageService {
    public static void main(String[] args) {
        var bath = new BatchSendMessageService();
        try(var kafka = new KafkaService<>("ECOMMERCE_SEND_MASSAGE_TO_ALL_USERS", bath::parse, BatchSendMessageService.class.getSimpleName(), Map.of())){
            kafka.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private final KafkaDispatcher<User> dispatcheroUser = new KafkaDispatcher<>();
    private final UserService service = new UserService();

    private void parse(ConsumerRecord<String,Message<String>> record) throws ExecutionException, InterruptedException {
        System.out.println("---------------------");
        System.out.println("Processing new batch");
        var message = record.value().getPayLond();
        System.out.println(record.value());
        System.out.println(record.key());
        for(User user:getAllUser()){
            dispatcheroUser.send(message,user.getUuid(),user, record.value().getCorrelationId().continueWith(BatchSendMessageService.class.getSimpleName()));
        }

    }

    private List<User> getAllUser() {
        return this.service.allUser();
    }
}
