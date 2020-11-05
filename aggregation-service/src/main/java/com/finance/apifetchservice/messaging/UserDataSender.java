package com.finance.apifetchservice.messaging;

import com.finance.apifetchservice.dto.UserDto;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;

import java.util.concurrent.ExecutionException;

public interface UserDataSender {
    AsyncRabbitTemplate.RabbitConverterFuture<?> send(UserDto userDto) throws ExecutionException, InterruptedException;
}
