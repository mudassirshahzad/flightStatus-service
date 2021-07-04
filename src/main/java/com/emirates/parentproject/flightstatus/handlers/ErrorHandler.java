package com.emirates.parentproject.flightstatus.handlers;

/*
 * flightStatusService
 */

import com.emirates.parentproject.flightstatus.bean.ServiceRestBean;
import com.emirates.parentproject.flightstatus.exceptions.DomainBaseException;
import com.emirates.parentproject.flightstatus.exceptions.PathNotFoundException;
import com.emirates.parentproject.flightstatus.exceptions.ThrowableTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * The type Error handler.
 */
@Slf4j
@Component
public class ErrorHandler {

    private static final String NOT_FOUND    = "path not found";
    private static final String ERROR_RAISED = "service error has been raised";

    /**
     * Not found mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> notFound(final ServerRequest request) {
        final DomainBaseException exception = new PathNotFoundException(new Exception(NOT_FOUND));
        log.error(NOT_FOUND, exception);
        return Mono.just(exception).transform(this::getResponse);
    }

    /**
     * Throwable error mono.
     *
     * @param error the error
     * @return the mono
     */
    Mono<ServerResponse> throwableError(final Throwable error) {
        log.error(ERROR_RAISED, error);
        return Mono.just(error).transform(this::getResponse);
    }

    @SuppressWarnings("unchecked")
    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> monoError) {
        return monoError
            .flatMap(error -> ThrowableTranslator.builder.get().and(builder -> builder.withThrowableError(error)).translate())
            .flatMap(translation -> ServerResponse
                .status(translation.getHttpStatus())
                .body(Mono.just(ServiceRestBean.builder().errors(translation.getErrors()).build()), ServiceRestBean.class)
            );
    }
}
