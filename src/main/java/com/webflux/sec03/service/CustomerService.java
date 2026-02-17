package com.webflux.sec03.service;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 17-02-2026
 */

import com.webflux.sec03.dto.CustomerDto;
import com.webflux.sec03.mapper.EntityDtoMapper;
import com.webflux.sec03.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .map(c -> EntityDtoMapper.toDto(c));
    }

    public Mono<CustomerDto> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(c -> EntityDtoMapper.toDto(c));
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono.map(c -> EntityDtoMapper.toEntity(c))
                .flatMap(entity -> customerRepository.save(entity))
                .map(customer -> EntityDtoMapper.toDto(customer));
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> customerDtoMono) {
        return customerRepository.findById(id)
                .flatMap(entity -> customerDtoMono)
                .map(customer -> EntityDtoMapper.toEntity(customer))
                .doOnNext(c -> c.setId(id))
                .flatMap(customer -> customerRepository.save(customer))
                .map(c -> EntityDtoMapper.toDto(c));
    }

    public Mono<Void> deleteCustomerById(Integer id) {
        return customerRepository.deleteById(id);
    }
}
