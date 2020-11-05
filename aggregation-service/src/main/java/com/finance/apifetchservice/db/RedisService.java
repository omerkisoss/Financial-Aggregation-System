package com.finance.apifetchservice.db;

import com.finance.apifetchservice.domain.Account;
import com.finance.apifetchservice.domain.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RedisService {

    private final RedisRepository redisRepository;

    @Autowired
    public RedisService(RedisRepository redisRepository){
        this.redisRepository = redisRepository;
    }

    public void saveToDB(Accounts accounts){
        for(Account account: accounts.getAccounts()){
            redisRepository.save(account);
        }

    }
    public Account readFromDB(String id){
        Optional<Account> account = redisRepository.findById(id);
        return account.orElse(null);

    }

    public Accounts readAllFromDB() {
        return new Accounts((ArrayList<Account>) redisRepository.findAll());
    }
}
