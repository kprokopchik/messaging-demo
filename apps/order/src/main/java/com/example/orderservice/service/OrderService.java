package com.example.orderservice.service;

import com.example.orderservice.messaging.DeductUserQuotaEvent;
import com.example.orderservice.messaging.DeductUserQuotaPublisher;
import com.example.orderservice.messaging.ReserveItemEvent;
import com.example.orderservice.messaging.ReserveItemPublisher;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderContent;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.remote.InventoryService;
import com.example.orderservice.remote.UserService;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final ReserveItemPublisher reserveItemsPublisher;
    private final DeductUserQuotaPublisher deductUserQuotaPublisher;

    @Transactional
    public void createOrderAsync(Order order) {
        order.setStatus(OrderStatus.DRAFT);
        for (OrderContent orderContent : order.getOrderContent()) {
            orderContent.setOrderId(order.getId());
        }
        order.setItemsReserved(false);
        order.setUserQuotaDeducted(false);
        order = orderRepository.saveAndFlush(order);
        reserveItemsPublisher.send(new ReserveItemEvent(order, null));
        deductUserQuotaPublisher.send(new DeductUserQuotaEvent(order, null));
    }

    @Transactional
    public void updateOrder(String orderId, Consumer<Order> modifier) {
        log.info("T start");
        orderRepository.findById(orderId)
                .ifPresent(order -> {
                    if (order.getStatus() != OrderStatus.DRAFT) {
                        log.warn("Can not update order having status: {}", order.getStatus());
                        return;
                    }
                    log.info("Update order '{}'", orderId);
                    modifier.accept(order);
                    if (order.getItemsReserved() && order.getUserQuotaDeducted()) {
                        log.info("ORDER '{}' READY_FOR_DISPATCH", orderId);
                        order.setStatus(OrderStatus.READY_FOR_DISPATCH);
                    }
                    orderRepository.saveAndFlush(order);
                });
        log.info("T end");
    }

    public void cancelOrder(String orderId) {
        log.info("CANCEL ORDER: {}", orderId);
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            log.info("ODRER {} NOT FOUND", orderId);
        }
    }

    @Transactional
    public Order createOrder(Order order) {
        orderRepository.findById(order.getId())
                .ifPresent(storedOrder -> {
                    throw new RuntimeException("Order already exists: " + storedOrder);
                });

        order.getOrderContent().forEach(orderContent -> orderContent.setOrderId(order.getId()));

        inventoryService.reserveItems(order.getId(), order.getOrderContent());

        userService.updateUserQuota(order.getUserId(), order.getOrderContent());

        order.setStatus(OrderStatus.READY_FOR_DISPATCH);

        Order entity = orderRepository.save(order);

        return entity;
    }

}
