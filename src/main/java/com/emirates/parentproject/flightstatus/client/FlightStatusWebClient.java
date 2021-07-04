package com.emirates.parentproject.flightstatus.client;

import com.emirates.parentproject.flightstatus.bean.FlightStatusDAO;
import com.emirates.parentproject.flightstatus.bean.FlightStatusDownstreamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FlightStatusWebClient {

    @Autowired
    private WebClient webClient;

    public Mono<FlightStatusDownstreamResponse> getFlightInformationFromDownstream(FlightStatusDAO flightStatusDAO) {
        return webClient
                .post()
                .body(BodyInserters.fromValue(flightStatusDAO))
                .retrieve()
                .bodyToMono(FlightStatusDownstreamResponse.class);
    }
}
