package cn.kiwipeach.controller;

import cn.kiwipeach.beans.Apple;
import cn.kiwipeach.beans.Banana;
import cn.kiwipeach.beans.Fruit;
import cn.kiwipeach.properties.AuthorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello World Spring Boot
 *
 * @author Wujun
 * @create 2017/07/10
 **/
@RestController
public class HelloSpringBootController {

    @Value("${springboot.authorName:KiWiPeach}")
    private String authorName;

    @Autowired
    private AuthorProperties authorProperties;

    @Autowired
    private Apple apple;

    @Autowired
    private Banana banana;


    @GetMapping("hello")
    public String sayHello(){
        return "hello world Spring Boot,I coming!";
    }

    @GetMapping(value = "author")
    public String getAuthorName(){
        return "welcome "+authorName;
    }

    @GetMapping(value = "author/properties")
    public AuthorProperties getAuthorJSON(){
        System.out.println("获取AuthorProperties:");
        return authorProperties;
    }


    @PostMapping(value = "fruit/{type}")
    public Fruit getFruit(@PathVariable(value = "type")String type){
        if("apple".equals(type)){
            return apple;
        }else if("banana".equals(type)){
            return banana;
        }else {
            return null;
        }
    }





}
