package cn.kiiwii.framework.springcloud.microservice.consumer.feign.controller;

import cn.kiiwii.framework.springcloud.microservice.consumer.feign.model.User;
import cn.kiiwii.framework.springcloud.microservice.consumer.feign.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2017/4/18.
 */
@RestController
public class FeignController {
    @Autowired
    private UserFeignService userFeignService;
    @GetMapping("/feign/{id}")
    public User findByIdFeign(@PathVariable Long id) {
        User user = this.userFeignService.findByIdFeign(id);
        return user;
    }
}
