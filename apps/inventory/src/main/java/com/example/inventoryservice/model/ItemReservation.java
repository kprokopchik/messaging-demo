package com.example.inventoryservice.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemReservation {
    private String itemId;
    private Integer count;
}
