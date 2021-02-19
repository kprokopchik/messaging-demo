package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Dispatch;
import com.example.inventoryservice.model.DispatchStatus;
import com.example.inventoryservice.model.ItemReservation;
import com.example.inventoryservice.repository.DispatchRepository;
import com.example.inventoryservice.repository.ItemRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;
    private final DispatchRepository dispatchRepository;

    @Transactional
    public void reserveItems(String orderId, List<ItemReservation> reservations) {
        for (ItemReservation reservation : reservations) {
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
