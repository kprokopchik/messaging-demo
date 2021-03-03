package com.example.userservice.service;

import com.example.userservice.model.QuotaDelta;
import com.example.userservice.model.QuotaRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final QuotaRepository quotaRepository;

    /**
     * WARNING: current implementation violates idempotency requirements!
     * */
    @Transactional
    public void deductUserQuota(String userId, List<QuotaDelta> deductions) {
        for (QuotaDelta deduction : deductions) {
            int updatedEntity = quotaRepository.decrement(userId, deduction.getItemId(), deduction.getCount());
            Preconditions.checkArgument(updatedEntity != 0,
                    "User %s does not have enough quota for %s %s",
                    userId, deduction.getCount(), deduction.getItemId()
            );
            log.info("User {} quota deducted for {} = {}", userId, deduction.getItemId(), deduction.getCount());
        }
    }
}
