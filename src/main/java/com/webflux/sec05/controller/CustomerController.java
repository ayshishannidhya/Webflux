package com.webflux.sec05.controller;

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

import com.webflux.sec05.dto.CustomerDto;
import com.webflux.sec05.exceptions.ApplicationException;
import com.webflux.sec05.filter.Category;
import com.webflux.sec05.service.CustomerService;
import com.webflux.sec05.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
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
    public Flux<CustomerDto> allCustomers(@RequestAttribute("category") Category category) {
        System.out.println(
                category
        );
        return service.getAllCustomers();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomers(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size) {
        return service.getAllCustomers(page, size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable Integer id) {
        return service.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {

        return customerDtoMono.transform(RequestValidator.validate())
                .as(c -> this.service.saveCustomer(c));
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Integer id,
                                            @RequestBody Mono<CustomerDto> customerDtoMono) {

        return customerDtoMono.transform(RequestValidator.validate())
                .as(validReq -> this.service.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

//    @DeleteMapping("{id}")
//    public Mono<ResponseEntity<Boolean>> deleteCustomer(@PathVariable Integer id) {
//        return service.deleteCustomerById(id);
//    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return service.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();
    }

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("working");
    }

}
