package com.finance.apifetchservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Account")
public class Account {
    @Id
    @JsonProperty("account")
    private String accountId;
    @JsonProperty("type")
    private String accountType;
    @JsonProperty("transactions")
    private ArrayList<Transaction> transactions;
    @JsonProperty("Blance")
    private double balance;

}
