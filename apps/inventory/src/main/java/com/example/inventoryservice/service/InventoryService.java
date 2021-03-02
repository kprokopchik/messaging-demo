package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Dispatch;
import com.example.inventoryservice.model.DispatchStatus;
import com.example.inventoryservice.model.ItemReservation;
import com.example.inventoryservice.repository.DispatchRepository;
import com.example.inventoryservice.repository.ItemRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ItemRepository itemRepository;
    private final DispatchRepository dispatchRepository;

    @Transactional
    public void cancelReservation(String orderId) {
        dispatchRepository.findByOrderId(orderId)
                .forEach(dispatch -> {
                    itemRepository.incrementItems(dispatch.getItemId(), dispatch.getCount());
                    dispatchRepository.delete(dispatch);
                });
    }

    @Transactional
    public void reserveItems(String orderId, List<ItemReservation> reservations) {
        for (ItemReservation reservation : reservations) {
            Optional<Dispatch> existing = dispatchRepository.findByOrderIdAndItemId(orderId, reservation.getItemId());
            if (existing.isPresent()) {
                log.info("Dispatch for order {} item {} exists: {}",
                        orderId, existing.get().getItemId(), existing.get().getStatus());
                return;
            }
            int updatedEntity = itemRepository.decrementItems(reservation.getItemId(), reservation.getCount());
            Preconditions.checkArgument(updatedEntity != 0,
                    "Not enough items - can not reserve %s of %s",
                    reservation.getCount(), reservation.getItemId());
            Dispatch dispatch = new Dispatch(null,
                    orderId,
                    reservation.getItemId(),
                    reservation.getCount(),
                    DispatchStatus.RESERVED_FOR_DISPATCH
            );
            dispatchRepository.save(dispatch);
        }
    }
}
