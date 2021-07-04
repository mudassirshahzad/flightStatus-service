package com.emirates.parentproject.flightstatus.routers.definitions;

/*
 * flightStatusService
 */

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

import com.emirates.parentproject.flightstatus.routers.StaticRouter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * The type Prod static router bean.
 */
@Component
@Profile("prod")
public class ProdStaticRouterBean implements StaticRouter {
    @Override public RouterFunction<?> doRoute() {
        return RouterFunctions.route(path(StaticRouter.ROUTE), req -> noContent().build());
    }
}
