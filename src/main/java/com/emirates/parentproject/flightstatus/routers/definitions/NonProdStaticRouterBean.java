package com.emirates.parentproject.flightstatus.routers.definitions;

/*
 * flightStatusService
 */

import static org.springframework.web.reactive.function.server.RouterFunctions.resources;

import com.emirates.parentproject.flightstatus.routers.StaticRouter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * The type Non prod static router bean.
 */
@Component
@Profile("!prod")
public class NonProdStaticRouterBean implements StaticRouter {
    @Override public RouterFunction<?> doRoute() {
        return resources(StaticRouter.ROUTE, new ClassPathResource(StaticRouter.PUBLIC));
    }
}
