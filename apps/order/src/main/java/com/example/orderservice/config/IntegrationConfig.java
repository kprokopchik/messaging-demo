package com.example.orderservice.config;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderConfirmation;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Autowired
    private OrderService orderService;

    /**
     * For external events source we will use reactive EmitterProcessor
     * https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_sending_arbitrary_data_to_an_output_e_g_foreign_event_driven_sources
     * */
    private final EmitterProcessor<Order> orderDraftPublisher = EmitterProcessor.create();

    @Bean
    public Consumer<Order> orderSink() {
        return order -> {
            log.info("in sink");
            orderDraftPublisher.onNext(order);
        };
    }

    /**
     * Supplier binding name: <functionName> + -out- + <index>
     * "orderCreatedDraft-out-0"
     * <p>
     * spring.cloud.stream.bindings.orderCreatedDraft-out-0.destination=my-topic
     * spring.cloud.stream.bindings.orderCreatedDraft-out-0.group=my-group
     * <p>
     * Docs: https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_suppliers_sources
     */
    @Bean
    public Supplier<Flux<Order>> onOrderCreatedDraft() {
        return () -> orderDraftPublisher.map(order -> {
            log.info("in supplier");
            return order;
        });
    }

    /**
     * Consumer binding name: <functionName> + -in- + <index>
     * "inventoryReservationCancelledForOrder-in-0"
     * <p>
     * spring.cloud.stream.bindings.inventoryReservationCancelledForOrder-in-0.destination=my-topic
     */
    @Bean
    public Consumer<Flux<OrderConfirmation>> onInventoryReservationCancelledForOrder() {
        return flux -> flux
                .doOnNext(event -> {
                    log.info("Order {} cancelled due to: {}", event.getOrderId(), event.getReason());
                    orderService.cancelOrder((event.getOrderId()));
                })
                .subscribe();
    }

    @Bean
    public Consumer<Flux<OrderConfirmation>> onQuotaDeductedForOrder() {
        return flux -> flux
                .doOnNext(event -> orderService.confirmOrder(event.getOrderId()))
                .subscribe();
    }

}
