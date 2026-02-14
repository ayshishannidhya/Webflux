package com.webflux.sec02.entity;

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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/*
    We do not have @Entity in R2DBC.
    @Table / @Column are not really required here. but adding it here for your reference...!

    Spring data 4.0 seems to expect table and column names in uppercase.
 */
@Getter
@Setter
@ToString
@Table("CUSTOMER")
public class Customer {

    @Id
    private Integer id;

    @Column("NAME")
    private String name;
    private String email;

}
