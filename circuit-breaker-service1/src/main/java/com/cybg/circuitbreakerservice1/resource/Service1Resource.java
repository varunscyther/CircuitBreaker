package com.cybg.circuitbreakerservice1.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.cybg.circuitbreakerservice1.hystrixfactory.FallBackRegistry;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/circuitbreaker/service1")
public class Service1Resource {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FallBackRegistry fallBackRegistry;

    @GetMapping("/level1")
    @HystrixCommand(fallbackMethod = "fallBackForLevel1", commandKey = "level1" , groupKey = "common-service")
    public String getData2() {

        if(RandomUtils.nextBoolean()) {
            throw new RuntimeException("Failed");
        }

        return "Level 1 service is responding........";
    }

    @GetMapping("/level2")
    @HystrixCommand(fallbackMethod = "fallBackForLevel2", commandKey = "level2" , groupKey = "common-service")
    public String getData() {

        return restTemplate.getForObject("http://localhost:9106/circuitbreaker/service2/getState", String.class);
    }

    public String fallBackForLevel1() {
        return fallBackRegistry.registerLevel1Service("level1");
    }

    public String fallBackForLevel2() {
        return fallBackRegistry.registerLevel2Service("level2");
    }

}
