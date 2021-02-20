package com.example.userservice.messaging;

import com.example.userservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductUserQuotaEvent {
    private Order order;
    private Boolean err;
}
