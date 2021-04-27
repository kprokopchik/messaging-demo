package com.example.inventoryservice.config;

import com.example.inventoryservice.model.Order;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Function<Order, Order> draftOrderCreated() {
        return order -> {
            log.info("Processing order: {}", order);
            try {
                inventoryService.reserveItems(order.getId(), order.getOrderContent());
                return order;
            } catch (Exception e) {
                log.warn("Something went wrong: {}", e.toString());
                streamBridge.send("inventoryReservationCancelled-out-0", order);
                return null;
            }
        };
    }
}
