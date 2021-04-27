package com.example.orderservice.config;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private OrderService orderService;

    @Bean
    public Consumer<Order> orderSink() {
        return order -> {
            log.info("Sending order to the event channel: {}", order);
            streamBridge.send("draftOrderCreated-out-0", order);
        };
    }

    @Bean
    public Consumer<Order> inventoryReservationCancelled() {
        return order -> {
            log.warn("Something went wrong: {}", order);
            orderService.cancelOrder(order.getId());
        };
    }

}
