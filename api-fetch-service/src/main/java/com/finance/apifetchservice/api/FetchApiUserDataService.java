package com.finance.apifetchservice.api;

import com.finance.apifetchservice.dto.Accounts;
import com.finance.apifetchservice.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class FetchApiUserDataService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://fakebanky.herokuapp.com/transactions";

    @Autowired
    public FetchApiUserDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("ApiTaskExecutor")
    public CompletableFuture<Accounts> fetchAccountFromApi(UserData userData) throws InterruptedException {
        System.out.println("current Thread: " + Thread.currentThread());
        Accounts accounts = restTemplate.getForObject(
                API_URL, Accounts.class);
        return CompletableFuture.completedFuture(accounts);
    }

}
