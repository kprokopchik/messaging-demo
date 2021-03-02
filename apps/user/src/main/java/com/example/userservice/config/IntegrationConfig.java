package com.example.userservice.config;

import com.example.userservice.exception.OrderFailedException;
import com.example.userservice.model.Order;
import com.example.userservice.model.OrderUpdate;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class IntegrationConfig {

    private final EmitterProcessor<OrderUpdate> cancelOrderEmitter = EmitterProcessor.create();

    @Autowired
    private UserService userService;

    @Bean
    public Function<Flux<Order>, Flux<OrderUpdate>> onInventoryReservationCreatedForOrder() {
        return flux -> flux
                .doOnNext(order -> {
                    try {
                        log.info("Got new order {} for user quota deduction", order);
                        userService.deductUserQuota(order.getUserId(), order.getOrderContent());
                    } catch (Exception e) {
                        throw new OrderFailedException(e.getMessage(), order);
                    }
                })
                .map(order -> new OrderUpdate(order.getId(), null))
                .onErrorResume(exception -> {
                    if (exception instanceof OrderFailedException) {
                        OrderFailedException err = (OrderFailedException) exception;
                        log.warn("User quota deduction failed for order {} due to {}", err.getOrder().getId(), err.getMessage());
                        cancelOrderEmitter.onNext(new OrderUpdate(err.getOrder().getId(), err.getMessage()));
                    }
                    return Mono.empty();
                })
                .share();
    }

    @Bean
    public Supplier<Flux<OrderUpdate>> onQuotaDeductionCancelledForOrder() {
        return () -> cancelOrderEmitter;
    }
}
