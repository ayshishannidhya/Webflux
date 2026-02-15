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
 * Created on: 15-02-2026
 */

import com.webflux.sec02.repo.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec03CustomerOrderRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void productsOrderedByCustomer() {
        customerOrderRepository.getProductOrderedByCustomer("mike")
                .doOnNext(p -> log.info("{}", p))
                .as(t -> StepVerifier.create(t))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void orderDetailsByProduct() {
        customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(dto -> log.info("{}", dto))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.amount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.amount()))
                .expectComplete()
                .verify();
    }
}
