package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Dispatch;
import com.example.inventoryservice.repository.DispatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dispatch")
@RequiredArgsConstructor
public class DispatchController {

    private final DispatchRepository dispatchRepository;

    @GetMapping("/{order_id}")
    public List<Dispatch> getByOrder(@PathVariable("order_id") String orderId) {
        return dispatchRepository.findByOrderId(orderId);
    }

    @PostMapping
    public Dispatch update(@RequestBody Dispatch entity) {
        return dispatchRepository.save(entity);
    }
}
