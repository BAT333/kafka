package org.example;

import org.example.consumer.KafkaService;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class ServiceProvider<T> implements Callable<Void> {
    public ServiceFactory<T> factory;
    public ServiceProvider(ServiceFactory<T> factory ) {
        this.factory =factory;
    }

    public Void call() throws Exception {
        var emailService = factory.creater();
        try( var service = new KafkaService<>(emailService.getTopic(),
                emailService::parse,
                emailService.getConsumerGrup(),
                Map.of())) {
            service.run();
        }
        return null;
    }

}
