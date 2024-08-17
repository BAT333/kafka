package org.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.example.Message;

import java.sql.SQLException;

public interface ConsumerFunction<T> {
    void consume(ConsumerRecord<String, Message<T>> record) throws Exception;
}
