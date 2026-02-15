package com.webflux.sec02.dto;

import java.time.Instant;
import java.util.UUID;

/*
 * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
 *
 * This source code is confidential and intended solely for internal use.
 * Unauthorized copying, modification, distribution, or disclosure of this
 * file, via any medium, is strictly prohibited.
 *
 * Project: Webflux
 * Author: Ayshi Shannidhya Panda
 * Created on: 15-02-2026
 */
public record OrderDetails(UUID orderId,
                           String customerName,
                           String productName,
                           Integer amount,
                           Instant orderDate) {
}
