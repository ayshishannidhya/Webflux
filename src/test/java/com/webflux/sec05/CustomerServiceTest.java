package com.webflux.sec05;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 17-03-2026
 */

import com.webflux.sec05.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
public class CustomerServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void unauthorized() {
        //no token
        webTestClient.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        //invalid token
        this.validateGet("secret", HttpStatus.UNAUTHORIZED);
    }

    private void validateGet(String token, HttpStatus expectedStatus) {
        webTestClient.get()
                .uri("/customers")
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    @Test
    public void primeCategory() {
        this.validateGet("secret456", HttpStatus.OK);
        this.validatePost("secret456", HttpStatus.OK);
    }

    private void validatePost(String token, HttpStatus expectedStatus) {

        var dto = new CustomerDto(null, "marshal", "marshal@gamil.com");

        webTestClient.post()
                .uri("/customers")
                .bodyValue(dto)
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    @Test
    public void starndardCategory() {
        this.validateGet("secret123", HttpStatus.OK);
        this.validatePost("secret123", HttpStatus.FORBIDDEN);
    }
}
