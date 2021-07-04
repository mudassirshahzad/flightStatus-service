package com.emirates.parentproject.flightstatus.bean;

/*
 * DomainError
 */

import lombok.Builder;
import lombok.Getter;

/**
 * The type Domain error.
 */
@Getter
@Builder
public class DomainError {

    private final String errorCode;
    private final String errorSummary;
    private final String errorMessage;

    @Override
    public String toString() {
        return String.format("[%s] {%s}", errorMessage, errorCode);
    }

}
