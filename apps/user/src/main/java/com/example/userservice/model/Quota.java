package com.example.userservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(
        name = "quota",
        indexes = {
                @Index(name = "user_item_idx", columnList = "user_id, item_id", unique = true)
        }
)
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "item_id")
    private String itemId;

    @Column
    private Integer count;
}
