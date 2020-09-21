package org.itkk.udf.core;

import org.itkk.udf.core.exception.handle.RmsResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 描述 : 系统入口
 *
 * @author wangkang
 */
@ServletComponentScan
@ComponentScan(basePackages = {"org.itkk"})
@EnableAsync
@RefreshScope
@EnableRetry
@EnableScheduling
public class BaseApplication {

    /**
     * 描述 : applicationConfig
     */
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 描述 : externalRestTemplate
     *
     * @param requestFactory requestFactory
     * @return RestTemplate
     */
    @Bean(name = "externalRestTemplate")
    public RestTemplate externalRestTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RmsResponseErrorHandler());
        return restTemplate;
    }

    /**
     * 描述 : requestFactory
     *
     * @return ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(applicationConfig.getBufferRequestBody());
        return requestFactory;
    }
}
