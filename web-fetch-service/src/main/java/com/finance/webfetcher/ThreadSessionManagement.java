package com.finance.webfetcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class ThreadSessionManagement {

    @Value( "${server.concurrent-sessions:10}" )
    private int numberOfSessions;

    @Bean("WebTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(numberOfSessions);
        log.info("NumberOfSessions: {}", numberOfSessions);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("WebTask-");
        executor.initialize();
        return executor;
    }
}
