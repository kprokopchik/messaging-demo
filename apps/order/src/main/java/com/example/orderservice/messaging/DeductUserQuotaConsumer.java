package com.example.orderservice.messaging;

import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DeductUserQuotaChannel.class)
@RequiredArgsConstructor
public class DeductUserQuotaConsumer {

    private final OrderService orderService;

    @StreamListener("deductUserQuotaChannelOut")
    public void handle(DeductUserQuotaEvent event) {
        if (Boolean.TRUE.equals(event.getErr())) {
            orderService.cancelOrder(event.getOrder().getId());
        } else {
            orderService.updateOrder(event.getOrder().getId(), order -> order.setUserQuotaDeducted(true));
        }
    }
}
