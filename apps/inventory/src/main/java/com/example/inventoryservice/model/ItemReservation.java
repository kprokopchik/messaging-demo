package com.example.inventoryservice.model;

import lombok.Data;

@Data
public class ItemReservation {
    private String itemId;
    private Integer count;
}
