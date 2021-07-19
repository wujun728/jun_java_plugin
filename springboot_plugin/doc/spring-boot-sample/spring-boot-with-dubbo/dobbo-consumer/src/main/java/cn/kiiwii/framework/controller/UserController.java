package cn.kiiwii.framework.controller;

import cn.kiiwii.framework.dubbo.api.IPerson;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2016/11/22.
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Reference(version = "1.0.0",check = false)
    IPerson person;

    @RequestMapping("/{id}")
    public String view(@PathVariable("id") int id) {
        String result = person.getNickName(id);
        return result;
    }
    @RequestMapping("/info/{name}")
    public String view(@PathVariable("name") String name) {
        String result = person.getFullName(name);
        return result;
    }
}
