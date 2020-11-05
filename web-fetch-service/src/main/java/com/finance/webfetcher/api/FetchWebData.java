package com.finance.webfetcher.api;

import com.finance.webfetcher.dto.Accounts;
import com.finance.webfetcher.dto.Transaction;
import com.finance.webfetcher.dto.UserData;
import com.finance.webfetcher.dto.Account;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class FetchWebData {

    private static final String WEB_URL = "https://fakebanky.herokuapp.com/fakebank";
    public static final String BODY = "body";
    public static final String H_2_CONTAINS_ACCOUNT_NAME = "h2:contains(Account name)";
    public static final String DIV_CONTAINS_BLANCE = "div:contains(Blance)";
    public static final String SPACE = " ";
    public static final String DIV_CONTAINS_ACCOUNT_NUMBER_IDS = "div:contains(Acccount Number (Ids))";
    public static final String DIV_CONTAINS_TRANSACTIONS = "div:contains(Transactions)";
    public static final String DIV = "div";
    public static final String NO_AVAILABLE_DATA = "N/A";

    @Async("WebTaskExecutor")
    public CompletableFuture<Accounts> fetchWebUserData(UserData userData) {
        Document document = new Document(WEB_URL);
        try {
            document = Jsoup.connect(document.location()).get();
            Elements divs = document.select(BODY);
            String s = divs.text();
            divs.forEach(Element::text);
        } catch (
                IOException e) {
            e.getMessage();
        }

        Elements accName = document.select(H_2_CONTAINS_ACCOUNT_NAME).get(0).children();
        Elements accBalance = document.select(DIV_CONTAINS_BLANCE).get(2).children();
        String[] balanceArray = accBalance.get(1).text().split(SPACE);

        Elements accNumber = document.select(DIV_CONTAINS_ACCOUNT_NUMBER_IDS).get(2).children();
        Elements accTransactions = document.select(DIV_CONTAINS_TRANSACTIONS).get(1).children();
        ArrayList<Transaction> transactionArrayList = getTransactions(accTransactions);

        Accounts fetchedAccounts = getAccountsFromFetchedData(new Account(accNumber.get(1).text(), NO_AVAILABLE_DATA, transactionArrayList,
                Double.parseDouble(balanceArray[0])));
        return CompletableFuture.completedFuture(fetchedAccounts);
    }

    private ArrayList<Transaction> getTransactions(Elements accTransactions) {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        for (Element accTransaction : accTransactions) {
            if (!accTransaction.select(DIV).isEmpty()) {
                Elements suspectedTransactions = accTransaction.select(DIV).get(0).children();
                if (suspectedTransactions.text().contains("Id") && suspectedTransactions.size() == 4) {
                    String id = suspectedTransactions.get(0).children().get(1).text();
                    String date = suspectedTransactions.get(1).children().get(1).text();
                    String amountWithCurrency = suspectedTransactions.get(2).children().get(1).text();
                    String[] amount = amountWithCurrency.split(SPACE);
                    String description = suspectedTransactions.get(3).children().get(1).text();
                    Transaction transaction = Transaction.builder().transactionId(Long.parseLong(id))
                            .transactionAmount(Double.parseDouble(amount[0]))
                            .transactionCurrency(amount[1])
                            .transactionDesc(description)
                            .transactionDate(date)
                            .build();
                    transactionArrayList.add(transaction);
                }
            }
        }
        return transactionArrayList;
    }

    private Accounts getAccountsFromFetchedData(Account fetchedAccount1) {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        accountArrayList.add(fetchedAccount1);
        Accounts fetchedAccounts = new Accounts();
        fetchedAccounts.setAccounts(accountArrayList);
        return fetchedAccounts;
    }
}
