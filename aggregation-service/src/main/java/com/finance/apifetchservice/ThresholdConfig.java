package com.finance.apifetchservice;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "threshold")
public class ThresholdConfig {

    private long webThreshold;
    private long apiThreshold;
    // getters and setters


    public long getWebThreshold() {
        return webThreshold;
    }

    public void setWebThreshold(long webThreshold) {
        this.webThreshold = webThreshold;
    }

    public long getApiThreshold() {
        return apiThreshold;
    }

    public void setApiThreshold(long apiThreshold) {
        this.apiThreshold = apiThreshold;
    }
}