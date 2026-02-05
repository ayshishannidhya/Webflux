package com.webflux.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.util.List;

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
@RequestMapping("/traditional")
@RestController
@Slf4j
public class TraditionalWebController {

    private final RestClient restClient = RestClient.builder()
            .requestFactory(new JdkClientHttpRequestFactory())// from spring boot 3.4.x
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("/products")
    public List<Product> getProducts() {
        var list = restClient.get()
                .uri("demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });

        log.info("received response: {}", list);
        return list;
    }

    @GetMapping("/products2")
    public Flux<Product> getProducts2() {
        var list = restClient.get()
                .uri("demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });

        log.info("received response: {}", list);
        return Flux.fromIterable(list);
    }
}
