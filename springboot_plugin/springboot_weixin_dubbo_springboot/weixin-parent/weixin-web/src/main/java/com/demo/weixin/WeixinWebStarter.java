package com.demo.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Wujun
 * @description
 * @date 2017/7/25
 * @since 1.0
 */

@SpringBootApplication(scanBasePackages = {"com.demo.weixin"})
@ImportResource(locations = {
        "classpath:/dubbo-consumer.xml"
})
public class WeixinWebStarter {

    public static void main(String[] args) {
        SpringApplication.run(WeixinWebStarter.class, args);
    }
}
