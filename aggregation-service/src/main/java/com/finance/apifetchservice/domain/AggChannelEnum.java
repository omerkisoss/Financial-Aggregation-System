package com.finance.apifetchservice.domain;

public enum AggChannelEnum {

    API("api"),
    WEB("web"),
    STATEMENT("statement");

    private String channel;

    AggChannelEnum(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}