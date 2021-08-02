package cn.kiiwii.framework.springcloud.microservice.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2017/4/25.
 */
@RestController
@RefreshScope
public class ConfigClientController {
    @Value("${name}")
    private String name;
    @GetMapping("/getName")
    public String getName() {
        return this.name;
    }
}
