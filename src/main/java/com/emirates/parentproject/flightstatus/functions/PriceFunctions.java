package com.emirates.parentproject.flightstatus.functions;

/*
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.PriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * The type General item interface.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PriceFunctions {

    @Autowired
    private PricingEngine pricingEngine;

    public final ToDoubleFunction<Tuple2<String, String>> callDownstreamF = (tuple) -> {

       return pricingEngine.getFlightPrice();
    };

    /**
     * The Price response transform f.
     */
    public final Function<Double, PriceResponse> priceResponseTransformF = (price) ->{

        return PriceResponse.builder().price(price).build();
    };

}
