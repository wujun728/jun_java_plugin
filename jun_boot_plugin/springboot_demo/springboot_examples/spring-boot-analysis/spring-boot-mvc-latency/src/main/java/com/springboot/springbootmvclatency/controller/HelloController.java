package com.springboot.springbootmvclatency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Version: 1.0
 * @Desc:
 */
@RestController
public class HelloController {

    @GetMapping("/hello/{latency}")
    public String hello(@PathVariable Long latency) {
        try {
            TimeUnit.MILLISECONDS.sleep(latency);
        } catch (InterruptedException e) {
            return "Error during thread sleep!";
        }
        return "Hello World!";
    }
}
