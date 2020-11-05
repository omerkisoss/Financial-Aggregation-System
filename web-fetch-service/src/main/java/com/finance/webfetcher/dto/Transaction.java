package com.finance.webfetcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @JsonProperty("id")
    private long transactionId;
    @JsonProperty("amount")
    private double transactionAmount;
    @JsonProperty("currency")
    private String transactionCurrency;
    @JsonProperty("Balance")
    private long transactionBalance;
    @JsonProperty("Description")
    private String transactionDesc;
    @JsonProperty("Date")
    private String transactionDate;

}
