package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Item;
import com.example.inventoryservice.model.ItemReservation;
import com.example.inventoryservice.repository.ItemRepository;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public Item getOne(@PathVariable("id") String id) {
        return itemRepository.findById(id)
                .orElse(null);
    }

    @PostMapping("/reserve/{order_id}")
    public void reserve(
            @PathVariable("order_id") String orderId,
            @RequestBody List<ItemReservation> reservations
    ) {
        inventoryService.reserveItems(orderId, reservations);
    }
}
