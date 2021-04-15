package com.example.inventoryservice.config;

import com.example.inventoryservice.model.Order;
import com.example.inventoryservice.model.OrderCancellation;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private InventoryService inventoryService;

    @Bean
    public Function<Order, Order> onOrderCreatedDraft() {
        return order -> {
            try {
                log.info("Got new order {} for reservation", order);
                inventoryService.reserveItems(order.getId(), order.getOrderContent());
                return order;
            } catch (Exception e) {
                log.warn("Item reservation failed for order {} due to {}", order.getId(), e.getMessage());
                streamBridge.send("onInventoryReservationCancelledForOrder-out-0", new OrderCancellation(order.getId(), e.getMessage()));
                return null;
            }
        };
    }

    @Bean
    public Consumer<OrderCancellation> onQuotaDeductionCancelledForOrder() {
        return orderCancellation -> {
            log.info("onQuotaDeductionCancelledForOrder: {}", orderCancellation);
            inventoryService.cancelReservation(orderCancellation.getOrderId());
            streamBridge.send("onInventoryReservationCancelledForOrder-out-0", orderCancellation);
        };
    }

}
