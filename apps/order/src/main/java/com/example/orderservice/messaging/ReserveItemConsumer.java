package com.example.orderservice.messaging;

import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReserveItemChannel.class)
@RequiredArgsConstructor
public class ReserveItemConsumer {

    private final OrderService orderService;

    @StreamListener("reserveItemChannelOut")
    public void handle(ReserveItemEvent event) {
        if (Boolean.TRUE.equals(event.getErr())) {
            orderService.cancelOrder(event.getOrder().getId());
        } else {
            orderService.updateOrder(event.getOrder().getId(), order -> order.setItemsReserved(true));
        }
    }
}
