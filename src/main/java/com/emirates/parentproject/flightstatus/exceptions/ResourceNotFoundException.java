package com.emirates.parentproject.flightstatus.exceptions;

/*
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.DomainDTO;
import com.emirates.parentproject.flightstatus.bean.DomainError;

import java.util.Collection;

/**
 * The type Resource not found exception.
 */
@DomainException(
    errorCode = "PARENT_PROJECT_CODE_003",
    errorMessage = "Given resource id have not found in repository")
public class ResourceNotFoundException extends DomainBaseException {

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     * @param cause    the cause
     */
    public ResourceNotFoundException(final DomainDTO baseBean, final Collection<DomainError> errors, final Throwable cause) {
        super(baseBean, errors, cause);
    }

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     */
    public ResourceNotFoundException(final DomainDTO baseBean, final Collection<DomainError> errors) {
        super(baseBean, errors);
    }

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param errors the errors
     */
    public ResourceNotFoundException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param cause the cause
     */
    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

}