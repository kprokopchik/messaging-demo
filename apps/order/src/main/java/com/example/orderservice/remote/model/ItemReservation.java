package com.example.orderservice.remote.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemReservation {

    private String orderId;

    private String itemId;

    private Integer count;
}
