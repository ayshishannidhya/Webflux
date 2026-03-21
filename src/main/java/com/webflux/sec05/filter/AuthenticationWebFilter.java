package com.webflux.sec05.filter;


import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

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
@Order(1)
public class AuthenticationWebFilter implements WebFilter {

    private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = exchange.getRequest().getHeaders().getFirst("auth-token");

        if (Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
            exchange.getAttributes().put("category", TOKEN_CATEGORY_MAP.get(token));
            return chain.filter(exchange);
        }
//        return exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
    }
}
