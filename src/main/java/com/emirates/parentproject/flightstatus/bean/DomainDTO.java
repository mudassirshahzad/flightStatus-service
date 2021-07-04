package com.emirates.parentproject.flightstatus.bean;

/*
 * DomainDTO
 */

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The interface Domain dto.
 *
 * @param <T> the type parameter
 */
public interface DomainDTO<T> {

    /**
     * Gets data.
     *
     * @return the data
     */
    T getData();

    /**
     * Gets errors.
     *
     * @return the errors
     */
    List<DomainError> getErrors();

    /**
     * Sets errors.
     *
     * @param domainErrors the domain errors
     */
    void setErrors(List<DomainError> domainErrors);

    /**
     * Validate domain dto.
     *
     * @return the domain dto
     */
    default DomainDTO<T> validate() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final javax.validation.Validator validator = factory.getValidator();
        final Set<ConstraintViolation<DomainDTO<T>>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            final List<DomainError> errors = new ArrayList<>();
            violations.forEach(violation -> errors.add(
                DomainError.builder()
                    .errorCode("400.006.001")
                    .errorMessage("Request is wrong")
                    .errorSummary(violation.getPropertyPath().toString() + " " + violation.getMessage())
                    .build()
            ));
            setErrors(errors);
        }
        return this;
    }
}
