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
 * Created on: 24-03-2026
 */

import com.webflux.sec06.exceptions.CustomerNotFoundException;
import com.webflux.sec06.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Service
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleException(CustomerNotFoundException ex, ServerRequest request) {
        return handleException(HttpStatus.NOT_FOUND, ex, request, problemDetail -> {

            problemDetail.setType(URI.create("https://example.com/problems/customer-not-found"));
            problemDetail.setTitle("Customer not found");
        });
    }

    private Mono<ServerResponse> handleException(HttpStatus status,
                                                 Exception ex,
                                                 ServerRequest request,
                                                 Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setInstance(URI.create(request.path()));
        consumer.accept(problem);
        return ServerResponse.status(status).bodyValue(problem);
    }

    public Mono<ServerResponse> handleException(InvalidInputException ex, ServerRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex, request, problemDetail -> {

            problemDetail.setType(URI.create("https://example.com/problems/invalid-input"));
            problemDetail.setTitle("Invalid input");
        });
    }

}
