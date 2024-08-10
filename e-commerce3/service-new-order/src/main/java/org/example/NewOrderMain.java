package org.example;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try( var dispatcherorder = new KafkaDispatcher<Order>();var dispatcher = new KafkaDispatcher<Email>()) {
           for(int i = 0;i<=3;i++){
               var userId = UUID.randomUUID().toString();
               var orderId = UUID.randomUUID().toString();
               var amount = BigDecimal.valueOf(Math.random() * 5000 + 1);
               var emails ="hagbcd@gmail.com";
               var order = new Order(userId,orderId, amount,emails);
               var email = new Email("subobject", "body");
               dispatcherorder.send("ECOMMERCE_NEW_ORDER_4",emails,order,new CorrelationId(NewOrderMain.class.getSimpleName()));
               dispatcher.send("ECOMMERCE_SEND_EMAIL_1",userId,email,new CorrelationId(NewOrderMain.class.getSimpleName()));
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}


