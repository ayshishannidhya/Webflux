package com.webflux.sec03.repository;

import com.webflux.sec03.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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
 * Created on: 05-02-2026
 */
@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {


     /*In ReactiveCrudRepository, delete methods return
    Mono<Void> or sometimes:

    Mono<Integer>   // rows affected (when using custom query)
    It does NOT automatically convert delete result into Boolean.*/

    @Modifying
    @Query("delete from customer where id=:id")
    Mono<Boolean> deleteCustomerById(Integer id);


    Flux<Customer> findBy(Pageable pageable);


}
