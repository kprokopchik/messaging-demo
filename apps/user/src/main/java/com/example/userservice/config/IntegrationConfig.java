package com.example.userservice.config;

import com.example.userservice.model.Order;
import com.example.userservice.model.OrderUpdate;
import com.example.userservice.service.UserService;
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
    private StreamBridge streamBridge;

    @Autowired
    private UserService userService;

    @Bean
    public Function<Order, OrderUpdate> onInventoryReservationCreatedForOrder() {
        return order -> {
            try {
                log.info("Got new order {} for user quota deduction", order);
                userService.deductUserQuota(order.getUserId(), order.getOrderContent());
                return new OrderUpdate(order.getId(), null);
            } catch (Exception e) {
                log.warn("User quota deduction failed for order {} due to {}", order.getId(), e.getMessage());
                streamBridge.send("onQuotaDeductionCancelledForOrder-out-0", new OrderUpdate(order.getId(), e.getMessage()));
                return null;
            }
        };
    }
}
