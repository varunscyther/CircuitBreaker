package com.cybg.circuitbreakerservice1.aspect;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixEventType;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Supplier;


@Aspect
@Configuration
public class LogMetricsDataAOP {

    @After("@annotation(com.cybg.circuitbreakerservice1.aspect.LogMetricsData) && args(registryKey,..)")
    public void logMetricsData(String registryKey) throws Throwable {
        hystrixCommandMetrics(registryKey);
    }

    private void hystrixCommandMetrics(String registryKey) {
        HystrixCommandKey HystrixCommandKey = () -> { return registryKey; };
        HystrixCommandMetrics metrics = HystrixCommandMetrics.getInstance(HystrixCommandKey);

        boolean isCircuitOpen = HystrixCircuitBreaker.Factory.getInstance(metrics.getCommandKey()).isOpen();
        Consumer<String> consumerStr = (s) -> System.out.println(s);
        consumerStr.accept(evaluateMetricsData(metrics, isCircuitOpen));
    }

    private String evaluateMetricsData(HystrixCommandMetrics metrics, boolean isCircuitOpen) {
        Supplier<String> s  = ()-> "++++++++++++++++++++++++++++++++ : " + "Hystrix metrics data :  "
                    + " isCircuitOpen : " + isCircuitOpen + "  group : " + metrics.getCommandGroup().name()
                    + " commandKey  : " + metrics.getCommandKey().name()
                    + " executionTimeMean : " + metrics.getExecutionTimeMean()
                    + " errorCount : " + metrics.getHealthCounts().getErrorCount()
                    + " : errorPercentage : " + metrics.getHealthCounts().getErrorPercentage()
                    + " : totalRequest : " + metrics.getHealthCounts().getTotalRequests()
                    + " : cumulativeCount :" + metrics.getCumulativeCount(HystrixEventType.SUCCESS)
                    + " : currentTime : " + System.currentTimeMillis()
                    + " : rollingCountCollapsedRequests : " + metrics.getRollingCount(HystrixEventType.SUCCESS)
                    + " : rollingMaxConcurrentExecutions : " + metrics.getRollingMaxConcurrentExecutions()
                    + " : currentConcurrentExecutionCount : " + metrics.getCurrentConcurrentExecutionCount()
                    + " : executionTimePercentile : " + metrics.getExecutionTimePercentile(95)
                    + " : totalTimeMean : " + metrics.getTotalTimeMean()
                    + " : totalTimePercentile : " + metrics.getTotalTimePercentile(95);
        return s.get();
    }

}
