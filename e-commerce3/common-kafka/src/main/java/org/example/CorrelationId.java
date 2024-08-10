package org.example;

import java.util.UUID;

public class CorrelationId {
    private final String id;

    public CorrelationId(String name) {
        this.id = name +"(" +UUID.randomUUID().toString() +")";
    }

    @Override
    public String toString() {
        return "CorrelationId{" +
                "id='" + id + '\'' +
                '}';
    }

    public CorrelationId continueWith(String correlationId) {
        return new CorrelationId(id+"-"+correlationId);

    }
}
