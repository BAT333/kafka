package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.consumer.KafkaService;
import org.example.dispatcher.KafkaDispatcher;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService implements ConsumerService<Order> {
    public static void main(String[] args) throws Exception {

        new ServiceRun(FraudDetectorService::new).start(5);
    }
    private final KafkaDispatcher<Order> dispatcher = new KafkaDispatcher<>();


    private boolean isFraud(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("4500"))>=0;
    }


    @Override
    public void parse(ConsumerRecord<String, Message<Order>> record) {
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
        if(this.isFraud(order.getPayLond().getAmount())){
            System.out.println("Order is a fraud");
            try {
                this.dispatcher.send("ECOMMERCE_NEW_ORDER_REJECTED",order.getCorrelationId().continueWith(FraudDetectorService.class.getSimpleName()).toString(),order.getPayLond(),new CorrelationId(FraudDetectorService.class.getSimpleName()));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Approved: "+ order);
            try {
                this.dispatcher.send("ECOMMERCE_NEW_ORDER_APPROVED",order.getCorrelationId().continueWith(FraudDetectorService.class.getSimpleName()).toString(),order.getPayLond(),new CorrelationId(FraudDetectorService.class.getSimpleName()));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER_4";
    }

    @Override
    public String getConsumerGrup() {
        return FraudDetectorService.class.getSimpleName();
    }
}
