package com.example.inventoryservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    private String id;

    @Column
    private Integer count;
}
