package com.example.inventoryservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ReserveItemChannel {

    @Input("reserveItemChannelIn")
    SubscribableChannel consume();

    @Output("reserveItemChannelOut")
    MessageChannel send();
}
