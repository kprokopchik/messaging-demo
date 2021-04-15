package com.example.orderservice.config;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderConfirmation;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Autowired
    private OrderService orderService;

    /**
     * For external events source we use reactive StreamBridge:
     * https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_sending_arbitrary_data_to_an_output_e_g_foreign_event_driven_sources
     * */
    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Consumer<Order> orderSink() {
        return order -> streamBridge.send("onOrderCreatedDraft-out-0", order);
    }

//    /**
//     * Supplier binding name: <functionName> + -out- + <index>
//     * "orderCreatedDraft-out-0"
//     * <p>
//     * spring.cloud.stream.bindings.orderCreatedDraft-out-0.destination=my-topic
//     * spring.cloud.stream.bindings.orderCreatedDraft-out-0.group=my-group
//     * <p>
//     * Docs: https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_suppliers_sources
//     */
//    @Bean
//    public Supplier<Order> onOrderCreatedDraft() {
//        return () -> {
//            try {
//                return orderDraftProcessor.take();
//            } catch (InterruptedException e) {
//                throw new RuntimeException("Interrupted", e);
//            }
//        };
//    }

    /**
     * Consumer binding name: <functionName> + -in- + <index>
     * "inventoryReservationCancelledForOrder-in-0"
     * <p>
     * spring.cloud.stream.bindings.inventoryReservationCancelledForOrder-in-0.destination=my-topic
     */
    @Bean
    public Consumer<OrderConfirmation> onInventoryReservationCancelledForOrder() {
        return event -> {
                log.info("Order {} cancelled due to: {}", event.getOrderId(), event.getReason());
                orderService.cancelOrder((event.getOrderId()));
        };
    }

    @Bean
    public Consumer<OrderConfirmation> onQuotaDeductedForOrder() {
        return event -> {
            log.info("Order {} confirmed", event.getOrderId());
            orderService.confirmOrder(event.getOrderId());
        };
    }

}
