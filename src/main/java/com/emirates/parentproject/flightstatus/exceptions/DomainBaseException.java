package com.emirates.parentproject.flightstatus.exceptions;

/*
 * flightStatusService
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.emirates.parentproject.flightstatus.bean.DomainDTO;
import com.emirates.parentproject.flightstatus.bean.DomainError;
import lombok.Getter;

/**
 * The type Domain base exception.
 */
@Getter
public class DomainBaseException extends RuntimeException {

    private final Collection<DomainError> errors = new ArrayList<>();
    private DomainDTO baseBean;

    /**
     * Instantiates a new Domain base exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     * @param cause    the cause
     */
    protected DomainBaseException(final DomainDTO baseBean, final Collection<DomainError> errors, final Throwable cause) {
        super(Optional.ofNullable(errors)
            .map(bffErrors -> bffErrors.stream()
                .map(DomainError::getErrorMessage)
                .collect(Collectors.joining(", ")))
            .orElse(DomainException.DEFAULT_MESSAGE), cause);

        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
        this.baseBean = baseBean;
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param baseBean the base bean
     * @param errors   the errors
     */
    protected DomainBaseException(final DomainDTO baseBean, final Collection<DomainError> errors) {
        super(Optional.ofNullable(errors)
            .map(bffErrors -> bffErrors.stream()
                .map(DomainError::getErrorMessage)
                .collect(Collectors.joining(", ")))
            .orElse(DomainException.DEFAULT_MESSAGE));

        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
        this.baseBean = baseBean;
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param errors the errors
     */
    protected DomainBaseException(final Collection<DomainError> errors) {
        super(Optional.ofNullable(errors)
            .map(bffErrors -> bffErrors.stream()
                .map(DomainError::getErrorMessage)
                .collect(Collectors.joining(", ")))
            .orElse(DomainException.DEFAULT_MESSAGE));

        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param cause the cause
     */
    protected DomainBaseException(final Throwable cause) {
        super(cause);
        initialise(Optional.of(cause));
    }

    private void initialise(final Optional<Throwable> optionalThrowable) {
        final Class<?> exception = this.getClass();
        if (exception.isAnnotationPresent(DomainException.class)) {
            final DomainException annotation = exception.getAnnotation(DomainException.class);
            errors.add(DomainError.builder()
                .errorMessage(String.format("%s - %s", annotation.errorMessage(), optionalThrowable.map(Throwable::getMessage).orElse("")))
                .errorCode(annotation.errorCode()).build());
        }
        else {
            errors.add(DomainError.builder()
                .errorMessage(DomainException.DEFAULT_MESSAGE)
                .errorCode(DomainException.DEFAULT_CODE).build());
        }
    }

}
