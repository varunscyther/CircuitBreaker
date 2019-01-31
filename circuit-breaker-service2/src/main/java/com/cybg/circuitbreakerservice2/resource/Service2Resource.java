package com.cybg.circuitbreakerservice2.resource;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/circuitbreaker/service2")
public class Service2Resource {

    @GetMapping("/getState")
    public String getState() {

        if(RandomUtils.nextBoolean()) {
            throw new RuntimeException("Failed");
        }
        return "Level 2 service is responding........";
    }
}
