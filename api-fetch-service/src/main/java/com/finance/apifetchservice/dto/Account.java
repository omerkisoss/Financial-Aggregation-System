package com.finance.apifetchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @JsonProperty("account")
    private String accountId;
    @JsonProperty("type")
    private String accountType;
    @JsonProperty("transactions")
    private ArrayList<Transaction> transactions;
    @JsonProperty("Blance")
    private double balance;

}
