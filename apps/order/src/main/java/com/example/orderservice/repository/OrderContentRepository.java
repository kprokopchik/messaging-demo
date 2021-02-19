package com.example.orderservice.repository;

import com.example.orderservice.model.OrderContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {
}
