package com.finance.apifetchservice.messaging.impl;

import com.finance.apifetchservice.domain.Accounts;
import com.finance.apifetchservice.dto.UserDto;
import com.finance.apifetchservice.messaging.UserDataSender;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
@Qualifier("rabbitMQSender")
public class UserDataSenderImpl implements UserDataSender {

    private final AsyncRabbitTemplate asyncRabbitTemplate;
    private final DirectExchange directExchange;
    private final static String API_ROUTING_KEY = "api";
    private final static String WEB_ROUTING_KEY = "web";

    public UserDataSenderImpl(AsyncRabbitTemplate asyncRabbitTemplate, DirectExchange directExchange) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.directExchange = directExchange;
    }

    @Override
    public AsyncRabbitTemplate.RabbitConverterFuture<?> send(UserDto userDto) throws ExecutionException, InterruptedException {
        String routingKey;
        switch (userDto.getAggChannel()){
            case API:
                routingKey = API_ROUTING_KEY;
                break;
            case WEB:
                routingKey = WEB_ROUTING_KEY;
                break;
            default:
                return null;

        }
        ListenableFuture<Accounts> listenableFuture =
                asyncRabbitTemplate.convertSendAndReceiveAsType(
                        directExchange.getName(),
                        routingKey,
                        userDto,
                        new ParameterizedTypeReference<Accounts>() {
                        });
        return (AsyncRabbitTemplate.RabbitConverterFuture<?>) listenableFuture;
    }
}
