package com.example.inventoryservice.messaging;

import com.example.inventoryservice.model.Order;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReserveItemChannel.class)
@RequiredArgsConstructor
@Slf4j
public class ReserveItemProcessor {

    private final InventoryService inventoryService;
    private final ReserveItemChannel reserveItemChannel;

    @StreamListener("reserveItemChannelIn")
    @SendTo("reserveItemChannelOut")
    public ReserveItemEvent handle(ReserveItemEvent event) {
        ReserveItemEvent result = new ReserveItemEvent(new Order(), false);
        result.getOrder().setId(event.getOrder().getId());
        try {
            Order order = event.getOrder();
            log.info("Reserving for '{}': {}", order.getId(), order.getOrderContent());
            inventoryService.reserveItems(order.getId(), order.getOrderContent());
        } catch (Exception e) {
            log.error("Happened", e);
            result.setErr(true);
        }
        return result;
    }

}
