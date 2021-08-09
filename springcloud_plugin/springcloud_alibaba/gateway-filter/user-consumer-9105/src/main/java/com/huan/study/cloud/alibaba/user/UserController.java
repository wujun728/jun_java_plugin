package com.huan.study.cloud.alibaba.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author huan.fu 2020/10/24 - 11:21
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @GetMapping("getAllProduct")
    public String getAllProduct() {
        return restTemplate.getForObject("http://product-provider/product/findAll", String.class);
    }
}
