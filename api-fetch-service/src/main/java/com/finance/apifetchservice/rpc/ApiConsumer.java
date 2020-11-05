package com.finance.apifetchservice.rpc;

import com.finance.apifetchservice.api.FetchApiUserDataService;
import com.finance.apifetchservice.dto.UserData;
import com.finance.apifetchservice.dto.Accounts;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Component
public class ApiConsumer {
    private final FetchApiUserDataService fetchApiUserDataService;

    public ApiConsumer(FetchApiUserDataService fetchApiUserDataService) {
        this.fetchApiUserDataService = fetchApiUserDataService;
    }

    @RabbitListener(queues = "apiQueue", concurrency = "20")
    public Accounts receive(UserData userData) throws InterruptedException, ExecutionException {
        System.out.println("Received UserData Request");
        Accounts accounts = fetchApiUserDataService.fetchAccountFromApi(userData).get();
        return CompletableFuture.completedFuture(accounts).get();
    }
}
