package com.example.orderservice.integration;

import com.example.orderservice.model.Order;

import java.util.function.Consumer;

public interface OrderSink extends Consumer<Order> {
}
