package com.emirates.parentproject.flightstatus.functions;

/*
 * flightStatusService
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * The type PricingEngine.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PricingEngine {

    public Double getFlightPrice(){

        // Any flight price rules
        return 500.0;
    }
}
