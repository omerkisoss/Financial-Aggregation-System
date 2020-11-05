package com.finance.apifetchservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadSessionManagement {

    @Value( "${server.concurrent-sessions:20}" )
    private int numberOfSessions;

    @Bean("ApiTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(numberOfSessions);
        System.out.println("NumberOfSessions: " + numberOfSessions);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("ApiTask-");
        executor.initialize();
        return executor;
    }
}
