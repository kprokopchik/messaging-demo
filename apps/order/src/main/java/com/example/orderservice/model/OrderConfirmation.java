package com.example.orderservice.model;

import lombok.Data;

@Data
public class OrderConfirmation {
    private String orderId;
    private String reason;
}
