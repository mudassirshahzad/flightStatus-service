package com.emirates.parentproject.flightstatus.bean;

/*
 * flightStatusService
 */

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Service rest bean.
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServiceRestBean implements DomainDTO<FlightStatusRequest> {

    @Valid @NotNull
    private FlightStatusRequest data;

    private List<DomainError> errors;

}
