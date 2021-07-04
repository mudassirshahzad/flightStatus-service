package com.emirates.parentproject.flightstatus.exceptions;

/*
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.DomainDTO;
import com.emirates.parentproject.flightstatus.bean.DomainError;

import java.util.Collection;

/**
 * The type Path not found exception.
 */
@DomainException(
    errorCode = "PARENTSERVICE-0002",
    errorMessage = "requested path could no be found in application routers")
public class PathNotFoundException extends DomainBaseException {

    /**
     * Instantiates a new Path not found exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     * @param cause    the cause
     */
    public PathNotFoundException(final DomainDTO baseBean, final Collection<DomainError> errors, final Throwable cause) {
        super(baseBean, errors, cause);
    }

    /**
     * Instantiates a new Path not found exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     */
    public PathNotFoundException(final DomainDTO baseBean, final Collection<DomainError> errors) {
        super(baseBean, errors);
    }

    /**
     * Instantiates a new Path not found exception.
     *
     * @param errors the errors
     */
    public PathNotFoundException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Path not found exception.
     *
     * @param cause the cause
     */
    public PathNotFoundException(final Throwable cause) {
        super(cause);
    }

}
