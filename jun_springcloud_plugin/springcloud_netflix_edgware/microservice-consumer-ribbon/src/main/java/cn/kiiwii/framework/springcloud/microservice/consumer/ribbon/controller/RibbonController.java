package cn.kiiwii.framework.springcloud.microservice.consumer.ribbon.controller;

import cn.kiiwii.framework.springcloud.microservice.consumer.ribbon.model.User;
import cn.kiiwii.framework.springcloud.microservice.consumer.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2017/4/18.
 */
@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;
    @GetMapping("/ribbon/{id}")
    public User findById(@PathVariable Long id) {
        return this.ribbonService.findById(id);
    }
}
