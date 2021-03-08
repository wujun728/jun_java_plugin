package com.zh.springbootdesignpattern.service.observer.impl;

import com.zh.springbootdesignpattern.bo.OrderEvent;
import com.zh.springbootdesignpattern.service.observer.OrderObserverService;
import com.zh.springbootdesignpattern.service.observer.OrderSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单主体实现类
 * createOrder(): 原始业务耦合调用
 * createOrder2(): 使用观察者模式重构调用
 * createOrder3(): 使用spring监听器实现观察者模式重构调用
 * @author Wujun
 * @date 2019/7/18
 */
@Slf4j
@Service
public class OrderSubjectServiceImpl implements OrderSubjectService {

    private static List<OrderObserverService> list = new ArrayList<>(16);

    @Autowired
    @Qualifier("smsObserverService")
    private OrderObserverService smsObserverService;

    @Autowired
    @Qualifier("weChatObserverService")
    private OrderObserverService weChatObserverService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init(){
        list.add(this.smsObserverService);
        list.add(this.weChatObserverService);
    }

    private void afterCreateOrder(){
        list.forEach(OrderObserverService :: afterCreateOrder);
    }

    @Override
    public void createOrder() {
        log.info("订单创建成功!");
        log.info("发送微信通知运营专员!");
        log.info("发送短信通知运营专员!");
    }

    @Override
    public void createOrder2() {
        log.info("订单创建成功!");
        this.afterCreateOrder();
    }

    @Override
    public void createOrder3() {
        log.info("订单创建成功!");
        this.applicationContext.publishEvent(new OrderEvent(this,"no:111"));
    }

}
