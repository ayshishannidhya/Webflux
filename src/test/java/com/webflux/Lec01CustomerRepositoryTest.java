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
 * Created on: 06-02-2026
 */

import com.webflux.sec02.entity.Customer;
import com.webflux.sec02.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01CustomerRepositoryTest extends AbstractTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAll() {
        customerRepository.findAll()
                .doOnNext(c -> log.info("{} ", c))
                .as(s -> StepVerifier.create(s))
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        customerRepository.findById(2)
                .doOnNext(c -> log.info("{} ", c))
                .as(s -> StepVerifier.create(s))
                .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByName() {
        customerRepository.findByName("jake")
                .doOnNext(c -> log.info("{} ", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions
                        .assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(c -> log.info("{} ", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions
                        .assertEquals("mike@gmail.com", c.getEmail()))
                .assertNext(c -> Assertions
                        .assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDeleteCustomer() {
        //insert
        var customer = new Customer();
        customer.setName("Marshall");
        customer.setEmail("marshall@gmail.com");
        customerRepository.save(customer)
                .doOnNext(c -> log.info("{} ", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();

        //count
        customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        //delete
        customerRepository.deleteById(11)
                .then(customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();
    }

    @Test
    public void updateCustomer() {
        customerRepository.findByName("ethan")
                .doOnNext(c -> c.setName("noel"))
                .flatMap(c -> customerRepository.save(c))
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("noel", c.getName()))
                .expectComplete()
                .verify();
    }

}
