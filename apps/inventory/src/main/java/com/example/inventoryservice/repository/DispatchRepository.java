package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, String> {

    Optional<Dispatch> findByOrderId(String orderId);
}
