package com.example.orderservice.remote.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemReservation {

    private String orderId;

    private String itemId;

    private Integer count;
}
