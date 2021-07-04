package com.emirates.parentproject.flightstatus.functions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class PriceFunctionsTest {

    @InjectMocks
    private PriceFunctions priceFunctions;

    @Mock
    private PricingEngine pricingEngine;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCallDownstreamF() {

        Double responsePrice = 1000.0;
        Answer<Double> answer = invocation -> {
            allocate3GBMemory();
            return responsePrice;
        };

        when(pricingEngine.getFlightPrice()).thenAnswer(answer).thenAnswer(answer);

        // Warmup call
        priceFunctions.callDownstreamF.applyAsDouble(Tuples.of("FlightNumber", "Date"));

        // Actual call
        Double price = priceFunctions.callDownstreamF.applyAsDouble(Tuples.of("FlightNumber", "Date"));
        assertEquals(price, responsePrice);
    }

    static class FiftyMB{
        byte[] data = new byte[50 * 1024 * 1024];
    }

    private void allocate3GBMemory(){

        log.info("Heap - Total memory reserved (GBs) = " + Runtime.getRuntime().totalMemory() / 1024 / 1024 / 1024);

        List<FiftyMB> objs = new ArrayList();

        long startMillis = System.currentTimeMillis();
        IntStream.rangeClosed(1, 50).parallel().forEach(obj -> objs.add(new FiftyMB()));

        long responseTime = System.currentTimeMillis() - startMillis;
        log.info("Total time taken = " + responseTime + " ms");

        log.info("Heap - Total memory reserved (GBs) = " + Runtime.getRuntime().totalMemory() / 1024 / 1024 / 1024);

    }
}