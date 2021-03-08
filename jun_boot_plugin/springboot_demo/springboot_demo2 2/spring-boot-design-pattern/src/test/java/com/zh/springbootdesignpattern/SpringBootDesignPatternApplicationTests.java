package com.zh.springbootdesignpattern;

import com.zh.springbootdesignpattern.service.observer.OrderSubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDesignPatternApplicationTests {

    @Autowired
    private OrderSubjectService orderSubjectService;

    @Test
    public void commonTest() {
        this.orderSubjectService.createOrder();
    }

    @Test
    public void observerTest() {
        this.orderSubjectService.createOrder2();
    }

    @Test
    public void observerWithListenerTest() {
        this.orderSubjectService.createOrder3();
    }

}
