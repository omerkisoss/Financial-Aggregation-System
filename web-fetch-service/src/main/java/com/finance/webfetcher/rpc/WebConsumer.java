package com.finance.webfetcher.rpc;

import com.finance.webfetcher.api.FetchWebData;
import com.finance.webfetcher.dto.Accounts;
import com.finance.webfetcher.dto.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class WebConsumer {
    private final FetchWebData fetchApiUserDataService;

    public WebConsumer(FetchWebData fetchWebUserDataService) {
        this.fetchApiUserDataService = fetchWebUserDataService;
    }

    @RabbitListener(queues = "webQueue", concurrency = "20")
    public Accounts receive(UserData userData) throws ExecutionException, InterruptedException {
        log.info("Fetching Web User Data");
        return fetchApiUserDataService.fetchWebUserData(userData).get();
    }
}
