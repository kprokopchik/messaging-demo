package com.example.orderservice.service;

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

import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final Optional<Consumer<Order>> orderSink;

    @Transactional
    public void createOrderAsync(Order order) {
        log.info("Requested order: {}", order);
        if (!orderSink.isPresent()) {
            log.error("OrderSink not configured");
            throw new IllegalStateException("OrderSink not configured");
        }

        order.setStatus(OrderStatus.DRAFT);
        for (OrderContent orderContent : order.getOrderContent()) {
            orderContent.setOrderId(order.getId());
        }
        order = orderRepository.saveAndFlush(order);
        orderSink.get().accept(order);
    }

    @Transactional
    public void confirmOrder(String orderId) {
        orderRepository.findById(orderId)
                .ifPresent(order -> {
                    if (order.getStatus() != OrderStatus.DRAFT) {
                        log.warn("Can not update order having status: {}", order.getStatus());
                        return;
                    }
                    order.setStatus(OrderStatus.READY_FOR_DISPATCH);
                    log.info("ORDER '{}' READY_FOR_DISPATCH", orderId);
                    orderRepository.saveAndFlush(order);
                });
    }

    public void cancelOrder(String orderId) {
        log.info("CANCEL ORDER: {}", orderId);
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            log.info("ORDER {} NOT FOUND", orderId);
        }
    }

    @Transactional
    public Order createOrder(Order order) {
        log.info("[SYNC] Requested order: {}", order);
        order.getOrderContent().forEach(orderContent -> orderContent.setOrderId(order.getId()));

        inventoryService.reserveItems(order.getId(), order.getOrderContent());

        userService.updateUserQuota(order.getUserId(), order.getOrderContent());

        order.setStatus(OrderStatus.READY_FOR_DISPATCH);

        Order entity = orderRepository.save(order);

        log.info("[SYNC] Order ready: {}", entity);
        return entity;
    }

}
