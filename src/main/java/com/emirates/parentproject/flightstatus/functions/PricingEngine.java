package com.emirates.parentproject.flightstatus.functions;

/*
 * flightStatusService
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type PricingEngine.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PricingEngine {

    private Double PRICE = 500.0;

    public Double getFlightPrice(){

        // Any flight price rules
        return PRICE;
    }
}
