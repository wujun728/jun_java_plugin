package com.springboot.springbootredis.controller;

import com.springboot.springbootredis.model.User;
import com.springboot.springbootredis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @Version: 1.0
 * @Desc:
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Serializable> redisCacheTemplate;

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public void test() {
        stringRedisTemplate.opsForValue().set("geekdigging", "https://www.geekdigging.com/");

        log.info("当前获取对象：{}",stringRedisTemplate.opsForValue().get("geekdigging"));

        redisCacheTemplate.opsForValue().set("geekdigging.com", new User(1L, "geekdigging", 18));

        User user = (User) redisCacheTemplate.opsForValue().get("geekdigging.com");

        log.info("当前获取对象：{}", user);
    }

    @GetMapping("/test1")
    public void test1() {
        User user = userService.save(new User(4L, "geekdigging.com", 35));

        log.info("当前 save 对象：{}", user);

        user = userService.get(1L);

        log.info("当前 get 对象：{}", user);

        userService.delete(5L);
    }

    @GetMapping("/getBlogUrl")
    public String getSessionId(HttpServletRequest request) {
        String url = (String) request.getSession().getAttribute("url");
        if (StringUtils.isEmpty(url)) {
            request.getSession().setAttribute("url", "https://www.geekdigging.com/");
        }
        log.info("获取session内容为： {}", request.getSession().getAttribute("url"));
        return request.getRequestedSessionId();
    }
}
