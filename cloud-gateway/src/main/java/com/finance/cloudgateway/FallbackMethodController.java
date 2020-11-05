package com.finance.cloudgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackMethodController {

    @GetMapping("/onDemandServiceFallback")
    public String onDemandServiceFallBack(){
        return "On-Demand Service is taking longer then expected." +
                " Please try again later";
    }
}
