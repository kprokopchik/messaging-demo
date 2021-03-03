package com.example.orderservice.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "order_t")
@ToString
public class Order {

    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column
    private OrderStatus status;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<OrderContent> orderContent;
}
