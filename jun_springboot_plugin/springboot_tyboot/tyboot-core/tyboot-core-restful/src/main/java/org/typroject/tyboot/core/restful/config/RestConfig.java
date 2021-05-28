package org.typroject.tyboot.core.restful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by yaohelang on 2017/10/12.
 */
@Configuration
public class RestConfig {


    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
              requestFactory.setConnectTimeout(3000);// 设置超时
                requestFactory.setReadTimeout(3000);
        return new RestTemplate(requestFactory);
    }
}
