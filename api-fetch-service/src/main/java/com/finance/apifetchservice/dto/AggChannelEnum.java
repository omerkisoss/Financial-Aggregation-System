package com.finance.apifetchservice.dto;

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