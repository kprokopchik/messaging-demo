package com.example.userservice.messaging;

import com.example.userservice.model.Order;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DeductUserQuotaChannel.class)
@RequiredArgsConstructor
@Slf4j
public class DeductUserQuotaProcessor {

    private final UserService userService;
    private final DeductUserQuotaChannel deductUserQuotaChannel;

    @StreamListener("deductUserQuotaChannelIn")
    @SendTo("deductUserQuotaChannelOut")
    public DeductUserQuotaEvent handle(DeductUserQuotaEvent event) {
        DeductUserQuotaEvent result = new DeductUserQuotaEvent(new Order(), false);
        result.getOrder().setId(event.getOrder().getId());
        try {
            Order order = event.getOrder();
            log.info("Deducting user '{}' quota for '{}': {}", order.getUserId(), order.getId(), order.getOrderContent());
            userService.deductUserQuota(order.getUserId(), order.getOrderContent());
        } catch (Exception e) {
            log.error("HAPPENED", e);
            result.setErr(true);
        }
        return result;
    }
}
