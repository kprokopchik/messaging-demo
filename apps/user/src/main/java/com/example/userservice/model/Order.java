package com.example.userservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String id;
    private String userId;
    private List<QuotaDelta> orderContent;
}
