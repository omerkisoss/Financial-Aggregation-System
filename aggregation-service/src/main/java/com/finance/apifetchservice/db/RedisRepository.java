package com.finance.apifetchservice.db;

import com.finance.apifetchservice.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedisRepository extends CrudRepository<Account, String> {
    List<Account> findAll();
}
