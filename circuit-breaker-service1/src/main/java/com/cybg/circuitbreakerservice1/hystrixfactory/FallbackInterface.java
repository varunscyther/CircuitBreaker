package com.cybg.circuitbreakerservice1.hystrixfactory;

@FunctionalInterface
public interface FallbackInterface {
    String register(String hystricCommnadKey);
}
