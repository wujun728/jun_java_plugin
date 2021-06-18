package com.demo.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(scanBasePackages = {"com.demo.weixin"})
@ImportResource(locations = {
        "classpath:/dubbo-provider.xml",
        "classpath:/context-weixin.xml"
                })
@PropertySource(value = {"classpath:/qqconnectconfig.properties"})
public class WeixinServiceStart {

    public static void main(String[] args) {
        SpringApplication.run(WeixinServiceStart.class, args);
    }
}
