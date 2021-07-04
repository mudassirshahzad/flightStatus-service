package com.emirates.parentproject.flightstatus.exceptions;

/*
 * flightStatusService
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.emirates.parentproject.flightstatus.bean.DomainDTO;
import com.emirates.parentproject.flightstatus.bean.DomainError;
import lombok.Getter;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * The type Throwable translator.
 */
@Getter
public class ThrowableTranslator {

    private HttpStatus        httpStatus;
    private DomainDTO responseBean;
    private List<DomainError> errors = new ArrayList<>();

    /**
     * The Builder.
     */
    public static Supplier<ThrowableTranslatorBuilder> builder = ThrowableTranslator.ThrowableTranslatorBuilder::new;

    /**
     * The type Throwable translator builder.
     */
    public static class ThrowableTranslatorBuilder {

        /**
         * The Itself.
         */
        ThrowableTranslator itself;

        /**
         * With throwable error.
         *
         * @param error the error
         */
        public void withThrowableError(final Throwable error) {

            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

            if (error instanceof IllegalArgumentException) {
                httpStatus = HttpStatus.BAD_REQUEST;
            }
            else if (error instanceof OptimisticLockingFailureException) {
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else if (error instanceof PathNotFoundException) {
                httpStatus = HttpStatus.BAD_REQUEST;
            }
            else if (error instanceof ResourceNotFoundException) {
                httpStatus = HttpStatus.NOT_FOUND;
            }

            if (error instanceof DomainBaseException) {

                final DomainBaseException exception = (DomainBaseException) error;
                itself.responseBean = exception.getBaseBean();
                itself.errors.addAll(exception.getErrors());

            }
            else {
                itself.errors.add(DomainError.builder()
                    .errorMessage(error.getMessage())
                    .errorCode(DomainException.DEFAULT_CODE)
                    .build());
            }

            itself.httpStatus = httpStatus;
        }

        /**
         * Instantiates a new Throwable translator builder.
         */
        ThrowableTranslatorBuilder() {
            this.itself = new ThrowableTranslator();
        }

        /**
         * And throwable translator builder.
         *
         * @param input the input
         * @return the throwable translator builder
         */
        public ThrowableTranslatorBuilder and(final Consumer<ThrowableTranslatorBuilder> input) {
            input.accept(this);
            return this;
        }

        /**
         * Translate mono.
         *
         * @return the mono
         */
        public Mono<ThrowableTranslator> translate() {
            // do your object validation here
            return Mono.just(itself);
        }
    }
}
