package com.example.inventoryservice.config;

import com.example.inventoryservice.exception.OrderFailedException;
import com.example.inventoryservice.model.Order;
import com.example.inventoryservice.model.OrderUpdate;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class IntegrationConfig {

    private final EmitterProcessor<OrderUpdate> cancelOrderEmitter = EmitterProcessor.create();

    @Autowired
    private InventoryService inventoryService;


    @Bean
    public Function<Flux<Order>, Flux<Order>> onOrderCreatedDraft() {
        return flux -> flux
                .doOnNext(order -> {
                    try {
                        log.info("Got new order {} for reservation", order);
                        inventoryService.reserveItems(order.getId(), order.getOrderContent());
                    } catch (Exception e) {
                        throw new OrderFailedException(e.toString(), order);
                    }
                })
                .onErrorResume(exception -> {
                    if (exception instanceof OrderFailedException) {
                        OrderFailedException err = (OrderFailedException) exception;
                        log.warn("Item reservation failed for order {} due to {}", err.getOrder().getId(), err.getMessage());
                        cancelOrderEmitter.onNext(new OrderUpdate(err.getOrder().getId(), err.getMessage()));
                    }
                    return Mono.empty();
                })
                .share();
    }

    @Bean
    public Consumer<Flux<OrderUpdate>> onQuotaDeductionCancelledForOrder() {
        return flux -> flux
                .doOnNext(orderUpdate -> inventoryService.cancelReservation(orderUpdate.getOrderId()))
                .doOnNext(cancelOrderEmitter::onNext);
    }

    @Bean
    public Supplier<Flux<OrderUpdate>> onInventoryReservationCancelledForOrder() {
        return () -> cancelOrderEmitter;
    }

}
