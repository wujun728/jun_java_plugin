package cn.centychen.quarkus.example.simple.service.impl;


import cn.centychen.quarkus.example.simple.service.HelloService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

/**
 * Created at 2019/5/18 by centychen<292462859@qq.com>
 */
@ApplicationScoped //标志Bean的作用域为一个应用一个实例。
@Default //默认，接口多实现时必须
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return String.format("Hello,%s!", name);
    }
}
