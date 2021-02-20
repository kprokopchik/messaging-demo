package com.example.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReserveItemChannel.class)
@RequiredArgsConstructor
public class ReserveItemPublisher {

    @Value("${queue.push.timeout.ms:1000}")
    private long pushTimeout;

    private final ReserveItemChannel reserveItemChannel;

    public void send(ReserveItemEvent event) {
        Message<ReserveItemEvent> message = MessageBuilder.withPayload(event).build();
        reserveItemChannel.send().send(message, pushTimeout);
    }
}
