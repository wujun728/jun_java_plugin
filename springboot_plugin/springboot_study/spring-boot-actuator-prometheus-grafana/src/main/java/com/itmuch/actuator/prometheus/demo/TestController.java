package com.itmuch.actuator.prometheus.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
