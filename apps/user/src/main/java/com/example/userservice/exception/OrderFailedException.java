package com.example.userservice.exception;

import com.example.userservice.model.Order;
import lombok.Data;

@Data
public class OrderFailedException extends RuntimeException {

    private final Order order;

    public OrderFailedException(String message, Order order) {
        super(message);
        this.order = order;
    }
}
