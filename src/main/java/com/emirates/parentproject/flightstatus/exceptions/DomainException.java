package com.emirates.parentproject.flightstatus.exceptions;

/*
 * flightStatusService
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Domain exception.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface DomainException {

    /**
     * The constant DEFAULT_MESSAGE.
     */
    String DEFAULT_MESSAGE  = "Unhandled exception has occurred";
    /**
     * The constant DEFAULT_CODE.
     */
    String DEFAULT_CODE     = "PARENT_PROJECT_CODE_001";

    /**
     * Error message string.
     *
     * @return the string
     */
    String errorMessage() default DEFAULT_MESSAGE;

    /**
     * Error code string.
     *
     * @return the string
     */
    String errorCode() default DEFAULT_CODE;

}
