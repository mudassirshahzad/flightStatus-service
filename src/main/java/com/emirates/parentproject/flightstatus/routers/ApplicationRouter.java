package com.emirates.parentproject.flightstatus.routers;

/*
 * flightStatusService
 */

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.emirates.parentproject.flightstatus.handlers.FlightStatusHandler;
import com.emirates.parentproject.flightstatus.handlers.ErrorHandler;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * The type Application router.
 */
@Slf4j
@Configuration
@EnableWebFlux
@RequiredArgsConstructor class ApplicationRouter {

    private static final String PARENT_PATH = "/flightStatus";

    private final StaticRouter    staticRouter;
    private final FlightStatusHandler flightStatusHandler;
    private final ErrorHandler    errorHandler;

    /**
     * Main router function router function.
     *
     * @return the router function
     */
    @Bean
    RouterFunction<?> mainRouterFunction() {

        final Consumer<ServerRequest> requestConsumer = serverRequest ->
            log.info(String.format("Request path %s params %s", serverRequest.path(), serverRequest.queryParams().toString()));

        return nest(path(PARENT_PATH), route(POST("/ﬂight"), flightStatusHandler::getFlightInformation))
                .andNest(path(PARENT_PATH), route(GET("/price/{flightNumber}/{date}"), flightStatusHandler::getPrice))
                .andOther(staticRouter.doRoute())
                .andOther(route(RequestPredicates.all(), errorHandler::notFound))
                .filter((request, next) -> {
                    requestConsumer.accept(request);
                    return next.handle(request);
                })
                ;
    }

}
