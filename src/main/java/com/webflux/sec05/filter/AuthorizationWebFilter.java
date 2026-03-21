package com.webflux.sec05.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 21-03-2026
 */
@Service
@Order(2)
public class AuthorizationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var category = exchange.getAttributeOrDefault("category", Category.STANDARD);
        return switch (category) {
            case PRIME -> prime(exchange, chain);
            case STANDARD -> standard(exchange, chain);
        };
    }

    public Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

    public Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
        var isGet = HttpMethod.GET.equals(exchange.getRequest().getMethod());
        if (isGet) {
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
    }
}
