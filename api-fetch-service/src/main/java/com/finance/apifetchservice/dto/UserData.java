package com.finance.apifetchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UserData {
        private String Id;
        private String username;
        private Date lastSuccessfulAggDate;
        private AggChannelEnum aggChannel;
    }


