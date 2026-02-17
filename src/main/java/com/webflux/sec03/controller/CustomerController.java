package com.webflux.sec03.controller;

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
import com.webflux.sec03.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public Flux<CustomerDto> allCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomers(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size) {
        return service.getAllCustomers(page, size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomer(@PathVariable Integer id) {
        return service.getCustomerById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return service.saveCustomer(customerDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable Integer id,
                                                            @RequestBody Mono<CustomerDto> customerDtoMono) {
        return service.updateCustomer(id, customerDtoMono)
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Boolean>> deleteCustomer(@PathVariable Integer id) {
        return service.deleteCustomerById(id);
    }

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("working");
    }

}
