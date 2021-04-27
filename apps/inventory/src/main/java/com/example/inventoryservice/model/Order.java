package com.example.inventoryservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private String id;
    private String userId;
    private List<ItemReservation> orderContent;

}
