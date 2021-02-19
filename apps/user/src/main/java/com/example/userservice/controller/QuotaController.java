package com.example.userservice.controller;

import com.example.userservice.model.Quota;
import com.example.userservice.model.QuotaDelta;
import com.example.userservice.model.QuotaRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quota")
@RequiredArgsConstructor
public class QuotaController {

    private final QuotaRepository quotaRepository;
    private final UserService userService;

    @GetMapping("/user/{user_id}/{item_id}")
    public Quota getByUserAndItemId(
            @PathVariable("user_id") String userId,
            @PathVariable("item_id") String itemId
    ) {
        return quotaRepository.findByUserIdAndItemId(userId, itemId);
    }

    @GetMapping("/user/{user_id}")
    public List<Quota> getByUser(
            @PathVariable("user_id") String userId
    ) {
        return quotaRepository.findAllByUserId(userId);
    }

    @PostMapping("/deduct/user/{user_id}")
    public void deductUserQuota(
            @PathVariable("user_id") String userId,
            @RequestBody List<QuotaDelta> deductions
    ) {
        userService.deductUserQuota(userId, deductions);
    }
}
