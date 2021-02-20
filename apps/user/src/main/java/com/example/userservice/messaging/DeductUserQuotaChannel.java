package com.example.userservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DeductUserQuotaChannel {

    @Input("deductUserQuotaChannelIn")
    SubscribableChannel consume();

    @Output("deductUserQuotaChannelOut")
    MessageChannel send();
}
