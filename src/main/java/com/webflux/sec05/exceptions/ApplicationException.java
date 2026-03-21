package com.webflux.sec05.exceptions;

import lombok.NonNull;
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
 * Created on: 20-03-2026
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public static <T> @NonNull Mono<T> customerNotFound(Integer id) {
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> @NonNull Mono<T> missingName() {
        return Mono.error(new InvalidInputException("Name is required"));
    }

    public static <T> @NonNull Mono<T> missingValidEmail() {
        return Mono.error(new InvalidInputException("valid email is required"));
    }
}
