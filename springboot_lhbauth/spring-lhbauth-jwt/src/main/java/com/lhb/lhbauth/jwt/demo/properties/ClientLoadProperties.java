package com.lhb.lhbauth.jwt.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @description
 * @date 2018/10/17 0017 11:40
 */
@Data
@Component
@ConfigurationProperties("system.client")
public class ClientLoadProperties {
    private ClientProperties[] clients = {};
}
