package com.finance.apifetchservice.domain;

import lombok.*;

import java.util.ArrayList;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    ArrayList <Account> accounts;
}
