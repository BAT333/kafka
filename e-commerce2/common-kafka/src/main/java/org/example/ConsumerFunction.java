package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;

import java.sql.SQLException;

public interface ConsumerFunction<T> {
    void consume(ConsumerRecord<String,T> record) throws Exception;
}
