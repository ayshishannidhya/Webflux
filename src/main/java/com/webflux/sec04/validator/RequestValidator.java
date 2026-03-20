package com.webflux.sec04.validator;

import com.webflux.sec04.dto.CustomerDto;
import com.webflux.sec04.exceptions.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 20-03-2026
 */
public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return customerDtoMono -> customerDtoMono
                .filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingValidEmail());
    }

    private static Predicate<CustomerDto> hasName() {
        return customerDto -> Objects.nonNull(customerDto.name());
    }

    private static Predicate<CustomerDto> hasValidEmail() {
        return customerDto ->
                Objects.nonNull(customerDto.email()) && customerDto.email().contains("@");
    }

}
