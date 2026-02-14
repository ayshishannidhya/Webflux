package com.webflux;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 14-02-2026
 */

import com.webflux.sec02.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

@Slf4j
public class Lec02ProductRepositoryTest extends AbstractTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findPriceRange() {
        productRepository.findByPriceBetween(750, 1000)
                .doOnNext(p -> log.info("{}", p))
                .as(c -> StepVerifier.create(c))
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    public void pageable() {
        productRepository
                .findAllBy(PageRequest.of(0, 3)
                        .withSort(Sort.by("price").ascending()))
                .doOnNext(p -> log.info("{}", p))
                .as(c -> StepVerifier.create(c))
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();
    }
}
