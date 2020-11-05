package com.finance.apifetchservice.dto;

import com.finance.apifetchservice.domain.AggChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String Id;
    private String username;
    private long lastSuccessfulAggDate;
    private AggChannelEnum aggChannel;
}
