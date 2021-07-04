package com.emirates.parentproject.flightstatus.routers;

/*
 * flightStatusService
 */

import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * The interface Static router.
 */
public interface StaticRouter {

    /**
     * The constant ROUTE.
     */
    String ROUTE  = "/**";
    /**
     * The constant PUBLIC.
     */
    String PUBLIC = "swagger/";

    /**
     * Do route router function.
     *
     * @return the router function
     */
    RouterFunction<?> doRoute();
}


