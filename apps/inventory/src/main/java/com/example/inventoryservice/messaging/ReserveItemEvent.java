package com.example.inventoryservice.messaging;

import com.example.inventoryservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveItemEvent {
    private Order order;
    private Boolean err;
}
