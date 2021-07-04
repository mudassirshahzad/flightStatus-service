package com.emirates.parentproject.flightstatus.functions;

/*
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.FlightStatusDAO;
import com.emirates.parentproject.flightstatus.bean.FlightStatusDownstreamResponse;
import com.emirates.parentproject.flightstatus.bean.FlightStatusRequest;
import com.emirates.parentproject.flightstatus.bean.FlightStatusResponse;
import com.emirates.parentproject.flightstatus.client.FlightStatusWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple5;

import javax.validation.Valid;
import java.util.Date;
import java.util.function.Function;

/**
 * The FlightStatusFunctions class.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlightStatusFunctions {

    @Autowired
    private FlightStatusWebClient flightStatusWebClient;

    /**
     * The flight status dto transform f.
     */
    public final Function<FlightStatusRequest, FlightStatusDAO> flightStatusDtoTransformF = (dto) ->
    {
        final @Valid Date date = dto.getFlightDate();
        return FlightStatusDAO.builder()
                        .flightDate(date)
                        .departureAirport(dto.getDepartureAirport())
                        .arrival(dto.getArrival())
                        .build();
    };

    public final Function<FlightStatusDAO, Mono<FlightStatusDownstreamResponse>> callFlightStatusFromDownstreamF = (dao) -> {

        Mono<FlightStatusDownstreamResponse> response1 = flightStatusWebClient.getFlightInformationFromDownstream(dao)
                .subscribeOn(Schedulers.elastic());
        Mono<FlightStatusDownstreamResponse> response2 = flightStatusWebClient.getFlightInformationFromDownstream(dao)
                .subscribeOn(Schedulers.elastic());
        Mono<FlightStatusDownstreamResponse> response3 = flightStatusWebClient.getFlightInformationFromDownstream(dao)
                .subscribeOn(Schedulers.elastic());
        Mono<FlightStatusDownstreamResponse> response4 = flightStatusWebClient.getFlightInformationFromDownstream(dao)
                .subscribeOn(Schedulers.elastic());
        Mono<FlightStatusDownstreamResponse> response5 = flightStatusWebClient.getFlightInformationFromDownstream(dao)
                .subscribeOn(Schedulers.elastic());

        return Mono.zip(response1, response2, response3, response4, response5)
                .map(Tuple5::getT1);
    };


    /**
     * The Flight status response transform f.
     */
    public final Function<Mono<FlightStatusDownstreamResponse>, Mono<FlightStatusResponse>> flightStatusResponseTransformF = (flightResponse) ->{

        return flightResponse.map(obj -> FlightStatusResponse.builder().flightNumber(obj.getFlightNumber()).build());
    };

}
