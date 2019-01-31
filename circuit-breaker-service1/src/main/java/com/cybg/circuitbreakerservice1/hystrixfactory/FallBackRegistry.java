package com.cybg.circuitbreakerservice1.hystrixfactory;

import com.cybg.circuitbreakerservice1.aspect.LogMetricsData;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FallBackRegistry {

    @LogMetricsData(registryKey = "level1")
    public String registerLevel1Service(String hystrixCmdKey) {
        return commonFallBackRegistryMessage(hystrixCmdKey);
    }


    @LogMetricsData(registryKey = "level2")
    public String registerLevel2Service(String hystrixCmdKey) {
        return commonFallBackRegistryMessage(hystrixCmdKey);
    }

    private String commonFallBackRegistryMessage(String hystrixCmdKey) {
        FallbackInterface fallbackInterface = (commandKey) -> "Circuit breaker fallback is initiated for : " + commandKey;
        return fallbackInterface.register(hystrixCmdKey);
    }

}
