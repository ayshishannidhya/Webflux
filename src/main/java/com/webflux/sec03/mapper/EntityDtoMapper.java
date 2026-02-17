package com.webflux.sec03.mapper;

import com.webflux.sec03.dto.CustomerDto;
import com.webflux.sec03.entity.Customer;

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
public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setEmail(dto.email());
        customer.setName(dto.name());
        customer.setId(dto.id());
        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

}
