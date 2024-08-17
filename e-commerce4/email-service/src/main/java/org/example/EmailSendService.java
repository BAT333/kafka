package org.example;


import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailSendService implements ConsumerService<String>{
    public static void main(String[] args) throws Exception {
        new ServiceRun(EmailSendService::new).start(5);
    }
    public String getTopic(){
        return "ECOMMERCE_SEND_EMAIL_1";
    }
    public String getConsumerGrup(){
        return EmailSendService.class.getSimpleName();
    }
    public void parse(ConsumerRecord<String,Message<String>> record){
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

    }

}
