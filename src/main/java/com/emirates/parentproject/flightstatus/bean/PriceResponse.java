package com.emirates.parentproject.flightstatus.bean;

/*
 * flightStatusService
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type PriceResponse bean.
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {

    private Double price;

}
