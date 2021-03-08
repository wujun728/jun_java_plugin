package com.springboot.springbootwebfluxlatency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/10/14
 * @Time: 20:36
 * @email: inwsy@hotmail.com
 * Description:
 */
@RestController
public class HelloController {
    @GetMapping("/hello/{latency}")
    public Mono<String> hello(@PathVariable int latency) {
        return Mono.just("Welcome to reactive ~")
                .delayElement(Duration.ofMillis(latency)); // 1
    }
}
