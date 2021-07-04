package com.emirates.parentproject.flightstatus.handlers;

/*-
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.FlightStatusResponse;
import com.emirates.parentproject.flightstatus.bean.ServiceRestBean;
import com.emirates.parentproject.flightstatus.exceptions.ResourceNotFoundException;
import com.emirates.parentproject.flightstatus.functions.FlightStatusFunctions;
import com.emirates.parentproject.flightstatus.functions.PriceFunctions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import javax.validation.Valid;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The type FlightStatusHandler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlightStatusHandler {

    private final ErrorHandler                 errorHandler;
    private final FlightStatusFunctions flightStatusFunctions;
    private final PriceFunctions priceFunctions;

    /**
     * Gets flight information.
     *
     * @param request the request
     * @return the flight information
     */
    public Mono<ServerResponse> getFlightInformation(final ServerRequest request) {
        return request.bodyToMono(ServiceRestBean.class)
                .map(ServiceRestBean::getData)
                .map(flightStatusFunctions.flightStatusDtoTransformF)
                .map(flightStatusFunctions.callFlightStatusFromDownstreamF)
                .flatMap(flightStatusFunctions.flightStatusResponseTransformF)
                .doOnNext(fsr -> log.info("Response body {}", fsr))
                .flatMap(flightStatusResponse -> ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(Mono.just(flightStatusResponse), FlightStatusResponse.class))
                .onErrorResume(errorHandler::throwableError)
                ;
    }

    /**
     * Gets the flight price.
     *
     * @param request the request
     * @return the price of the flight
     */
    public Mono<ServerResponse> getPrice(final ServerRequest request) {

        final @Valid String flightNumber = request.pathVariable("flightNumber");
        final @Valid String date = request.pathVariable("date");

        return Mono.just(Tuples.of(flightNumber, date))
                .map(priceFunctions.callDownstreamF)
                .map(priceFunctions.priceResponseTransformF)
                .flatMap(price -> ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(Mono.just(price), FlightStatusResponse.class))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                        new RuntimeException("No price information found for the mentioned flight number. " + flightNumber))))
                .onErrorResume(errorHandler::throwableError)
        ;
    }

}
