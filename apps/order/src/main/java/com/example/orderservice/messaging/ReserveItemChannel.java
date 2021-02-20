package com.example.orderservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ReserveItemChannel {

    @Output("reserveItemChannelIn")
    MessageChannel send();

    @Input("reserveItemChannelOut")
    SubscribableChannel receive();
}
