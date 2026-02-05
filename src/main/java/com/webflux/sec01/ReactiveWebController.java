package com.webflux.sec01;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 05-02-2026
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequestMapping("/reactive")
@RestController
@Slf4j
public class ReactiveWebController {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("/products")
    public Flux<Product> getProducts() {
        var flux = webClient.get()
                .uri("demo01/products")
                .retrieve()
                .bodyToFlux(Product.class) //response type will be flux
                .doOnNext(p -> log.info("received: {}", p));

        return flux;
    }
    
    //Now browser will understand that this is a streaming response
    @GetMapping(value = "/products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStream() {
        var flux = webClient.get()
                .uri("demo01/products")
                .retrieve()
                .bodyToFlux(Product.class) //response type will be flux
                .doOnNext(p -> log.info("received: {}", p));

        return flux;
    }
}
