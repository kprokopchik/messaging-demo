package com.example.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DeductUserQuotaChannel.class)
@RequiredArgsConstructor
public class DeductUserQuotaPublisher {

    private final DeductUserQuotaChannel deductUserQuotaChannel;

    @Value("${queue.push.timeout.ms:1000}")
    private long pushTimeout;

    public void send(DeductUserQuotaEvent event) {
        Message<DeductUserQuotaEvent> message = MessageBuilder.withPayload(event).build();
        deductUserQuotaChannel.send().send(message, pushTimeout);
    }
}
