package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("/{id}")
    public Optional<Order> getById(@PathVariable("id") String id) {
        Optional<Order> entity = orderRepository.findById(id);
        log.info("Search order '{}' result: {}", id, entity);
        return entity;
    }

    @GetMapping("/user/{user_id}")
    public List<Order> getByUser(@PathVariable("user_id") String userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @PostMapping
    public Order update(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PostMapping("/async")
    public void createOrderAsync(@RequestBody Order order) {
        orderService.createOrderAsync(order);
    }
}
