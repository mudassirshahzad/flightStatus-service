package com.emirates.parentproject.flightstatus.bean;

/*
 * flightStatusService
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The type FlightStatusRequest bean.
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FlightStatusRequest {

    @NotNull @NotEmpty
    private Date flightDate;

    @NotNull @NotEmpty
    private String departureAirport;

    @NotNull @NotEmpty
    private String arrival;

}
