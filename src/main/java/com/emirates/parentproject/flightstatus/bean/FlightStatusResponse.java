package com.emirates.parentproject.flightstatus.bean;

/*
 * flightStatusService
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type FlightStatusResponse bean.
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FlightStatusResponse {

    private String flightNumber;
}
