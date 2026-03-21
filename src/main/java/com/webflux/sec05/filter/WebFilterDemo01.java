//package com.webflux.sec05.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
/// *
// * Copyright (c) 2026 Ayshi Shannidhya Panda. All rights reserved.
// *
// * This source code is confidential and intended solely for internal use.
// * Unauthorized copying, modification, distribution, or disclosure of this
// * file, via any medium, is strictly prohibited.
// *
// * Project: Webflux
// * Author: Ayshi Shannidhya Panda
// * Created on: 21-03-2026
// */
//@Slf4j
//@Service
//@Order(2)//now it run at second
//public class WebFilterDemo01 implements WebFilter {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        log.info("received-1");
//        //return Mono.empty();  //it will stops here only
//        return chain.filter(exchange);//now it will goes to next filter
//    }
//}
