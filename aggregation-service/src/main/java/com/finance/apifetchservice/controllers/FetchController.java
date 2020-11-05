package com.finance.apifetchservice.controllers;

import com.finance.apifetchservice.db.RedisService;
import com.finance.apifetchservice.domain.Accounts;
import com.finance.apifetchservice.domain.AggChannelEnum;
import com.finance.apifetchservice.dto.UserDto;
import com.finance.apifetchservice.messaging.UserDataSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/")
public class FetchController {

    private final UserDataSender userDataSender;
    private final RedisService redisService;
    @Value("${threshold.api-threshold:10800000}")
    private long aggregationTimeApiThreshold;

    @Value("${threshold.web-threshold:3600000}")
    private long aggregationTimeWebThreshold;

    @Autowired
    public FetchController(@Qualifier("rabbitMQSender") UserDataSender userDataSender, RedisService redisService) {
        this.userDataSender = userDataSender;
        this.redisService = redisService;
    }

    @GetMapping("/ondemand")
    public Accounts getOnDemandData(@RequestParam(value = "userName") String userName,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "channel") AggChannelEnum channel,
                                    @RequestParam(value = "lsad") long lsad)  {
        log.info("Got User Fetch Request -   " +
                "userId: {}," +
                " userName: {}," +
                " channel: {}," +
                " lastSuccessfulAgg: {}", userId, userName, channel, lsad);
        UserDto userDto = UserDto.builder()
                .Id(userId)
                .username(userName)
                .aggChannel(channel)
                .lastSuccessfulAggDate(lsad)
                .build();
        Accounts accounts = null;
        log.info("checking if user is allowed to send on-demand request");
        if (checkFetchPermitted(channel, lsad)) {
            log.info("Sending User Data Fetch Request");
            try {
                accounts = (Accounts) userDataSender.send(userDto).get();
                if(accounts.getAccounts() != null) {
                    log.info("Got Data from {} Service saving in DB", channel);
                    redisService.saveToDB(accounts);
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage(), e);
            }

        } else {
            log.info("user with userId {} is not allowed to get on-demand Data. Fetching last Successful Data from DataBase", userId);
            accounts = redisService.readAllFromDB();
        }
        return accounts;
    }

    @RequestMapping("/data")
    public Accounts getDataController(@RequestParam(value = "userName") String userName,
                                      @RequestParam(value = "userId") String userId,
                                      @RequestParam(value = "channel") String channel) {
        log.info("Fetching Data from Database");
        return redisService.readAllFromDB();
    }

    private boolean checkFetchPermitted(AggChannelEnum channel, long lsad) {
        boolean isFetchPermitted = false;
        switch (channel) {
            case WEB:
                if (System.currentTimeMillis() - lsad > aggregationTimeWebThreshold)
                    isFetchPermitted = true;
                break;
            case API:
                if (System.currentTimeMillis() - lsad > aggregationTimeApiThreshold)
                    isFetchPermitted = true;
                break;

        }
        return isFetchPermitted;
    }
}
