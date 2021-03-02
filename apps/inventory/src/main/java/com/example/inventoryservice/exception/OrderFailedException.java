package com.example.inventoryservice.exception;

import com.example.inventoryservice.model.Order;
import lombok.Data;

@Data
public class OrderFailedException extends RuntimeException {

    private final Order order;

    public OrderFailedException(String message, Order order) {
        super(message);
        this.order = order;
    }
}
