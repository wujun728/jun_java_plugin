package com.zh.springbootredisson.controller;

import com.zh.springbootredisson.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/book")
    public String book(){
        return this.orderService.book();
    }

    @GetMapping("/bookWithLock")
    public String bookWithLock(){
        return this.orderService.bookWithLock();
    }
}
