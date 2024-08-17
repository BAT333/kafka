package org.example;

public class Message <T>{
    private final CorrelationId correlationId;
    private final T payLond;

    @Override
    public String toString() {
        return "Message{" +
                "correlationId=" + correlationId +
                ", payLond=" + payLond +
                '}';
    }

    public T getPayLond() {
        return payLond;
    }

    public CorrelationId getCorrelationId() {
        return correlationId;
    }

    public Message(T payLond, CorrelationId correlationId){
        this.correlationId = correlationId;
        this.payLond = payLond;
    }
}
