package org.example;

import java.math.BigDecimal;

public class Order {
    private final String userId, orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(String userId, String orderId, BigDecimal amount, String email) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }
}
