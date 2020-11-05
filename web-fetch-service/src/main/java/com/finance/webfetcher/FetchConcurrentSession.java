package com.finance.webfetcher;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "server")
public class FetchConcurrentSession {

    private int concurrentSessions;
    // getters and setters


    public int getConcurrentSessions() {
        return concurrentSessions;
    }

    public void setConcurrentSessions(int concurrentSessions) {
        this.concurrentSessions = concurrentSessions;
    }
}