package cn.kiiwii.framework.springcloud.microservice.consumer.feign.service;

import cn.kiiwii.framework.springcloud.microservice.consumer.feign.model.User;
import cn.kiiwii.framework.springcloud.microservice.consumer.feign.service.hystrix.FeignServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhong on 2017/4/18.
 */
@FeignClient(name = "microservice-provider-user",fallback =FeignServiceFallback.class)
public interface UserFeignService {

    @RequestMapping("/{id}")
    public User findByIdFeign(@RequestParam("id") Long id);
}
