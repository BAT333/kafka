package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ReadingReportService {
    private static final Path SOURCE = new File("C:\\Users\\rafae\\IdeaProjects\\e-commerce\\service-reading-report\\src\\main\\resources\\report.txt").toPath();
    public static void main(String[] args) {
        var readingReportService = new ReadingReportService();

        try ( var kafka = new KafkaService<>("ECOMMERCE_USER_GENERATE_READING_REPORT", readingReportService::parse, ReadingReportService.class.getSimpleName(), Map.of())){
            kafka.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void parse(ConsumerRecord<String,Message<User>> record){
        System.out.println("---------------------");
        System.out.println("Processing report for "+ record.value());
     /*   System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

      */
        try {

           var user = record.value().getPayLond();
            var target = new File(user.getTaget());
            IO.copyTo(SOURCE,target);
            IO.append(target," Createrd for", user.getUuid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
