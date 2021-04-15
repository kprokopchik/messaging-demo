package com.example.inventoryservice.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Order {
    private String id;
    private String userId;
    private List<ItemReservation> orderContent;
}
