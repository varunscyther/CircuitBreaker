package com.cybg.circuitbreakerservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableAutoConfiguration
public class CircuitBreakerService1Application {

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerService1Application.class, args);
	}

}

