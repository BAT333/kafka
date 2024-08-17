package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.consumer.KafkaService;
import org.example.dispatcher.KafkaDispatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageService implements ConsumerService<String>{
    public static void main(String[] args) throws Exception {
        new ServiceRun(BatchSendMessageService::new).start(5);
    }
    private final KafkaDispatcher<User> dispatcheroUser = new KafkaDispatcher<>();
    private final UserService service = new UserService();


    private List<User> getAllUser() {
        return this.service.allUser();
    }

    @Override
    public void parse(ConsumerRecord<String, Message<String>> record) {
        System.out.println("---------------------");
        System.out.println("Processing new batch");
        var message = record.value().getPayLond();
        System.out.println(record.value());
        System.out.println(record.key());
        for(User user:getAllUser()){
            try {
                dispatcheroUser.send(message,user.getUuid(),user, record.value().getCorrelationId().continueWith(BatchSendMessageService.class.getSimpleName()));
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_SEND_MASSAGE_TO_ALL_USERS";
    }

    @Override
    public String getConsumerGrup() {
        return BatchSendMessageService.class.getSimpleName();
    }
}
