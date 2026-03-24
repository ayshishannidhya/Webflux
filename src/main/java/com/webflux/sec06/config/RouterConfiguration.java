package com.webflux.sec06.config;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 22-03-2026
 */

import com.webflux.sec06.exceptions.CustomerNotFoundException;
import com.webflux.sec06.exceptions.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {

    private final CustomerRequestHandler customerRequestHandler;
    private final ApplicationExceptionHandler applicationExceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions.route()
                .GET("/customers", req -> customerRequestHandler.allCustomers(req))
                .GET("/customers/paginated", req -> customerRequestHandler.paginatedCustomers(req))
                .GET("/customers/{id}", req -> customerRequestHandler.getCustomer(req))
                .POST("/customers", req -> customerRequestHandler.saveCustomer(req))
                .PUT("/customers/{id}", req -> customerRequestHandler.updateCustomer(req))
                .DELETE("/customers/{id}", req -> customerRequestHandler.deleteCustomer(req))
                .onError(CustomerNotFoundException.class,
                        (ex, req) ->
                                applicationExceptionHandler.handleException(ex, req))
                .onError(InvalidInputException.class, applicationExceptionHandler::handleException)
                .filter(((request, next) -> {
                    return next.handle(request);//allows to go to next filter
//                    return ServerResponse.badRequest().build();// its just reject all the request
                }))
                .filter(((request, next) -> {
                    return next.handle(request);//allows to go to next filter
//                    return ServerResponse.badRequest().build();// its just reject all the request
                }))
                .build();
    }
}
