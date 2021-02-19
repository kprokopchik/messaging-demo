package com.example.orderservice.remote;

import com.example.orderservice.model.OrderContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "user-service",
        url = "${app.user-service.url}"
)
public interface UserService {

    @PostMapping(path = "/quota/deduct/user/{user_id}")
    void updateUserQuota(
            @PathVariable("user_id") String userId,
            @RequestBody List<OrderContent> items
    );

}
