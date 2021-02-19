package com.example.orderservice.remote;

import com.example.orderservice.model.OrderContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "order-service",
        url = "${app.inventory-service.url}"
)
public interface InventoryService {

    @PostMapping(path = "/item/reserve/{order_id}")
    void reserveItems(
            @PathVariable("order_id") String orderId,
            @RequestBody List<OrderContent> reservation
    );
}
