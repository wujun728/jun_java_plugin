package com.java1234.config;

import java.util.Random;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {

	/**
	 * 调用服务模版对象
	 * @return
	 */
	@Bean
	@LoadBalanced  // 引入ribbon负载均衡
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	/**
     * 自定义轮询算法
     * @return
     */
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
	
}
