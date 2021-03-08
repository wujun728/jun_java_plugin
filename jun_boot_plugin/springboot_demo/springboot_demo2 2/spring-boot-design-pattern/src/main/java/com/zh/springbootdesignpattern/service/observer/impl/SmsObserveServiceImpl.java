package com.zh.springbootdesignpattern.service.observer.impl;

import com.zh.springbootdesignpattern.bo.OrderEvent;
import com.zh.springbootdesignpattern.service.observer.OrderObserverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 短信类观察者
 * 使用原生观察和模式只需要实现OrderObserver接口
 * 使用spring监听器实现观察者需要实现ApplicationListener接口
 * @author Wujun
 * @date 2019/7/18
 */
@Slf4j
@Service("smsObserverService")
public class SmsObserveServiceImpl implements OrderObserverService,ApplicationListener<OrderEvent> {

    @Override
    public void afterCreateOrder() {
        log.info("发送短信通知运营专员!");
    }

    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        log.info("发送短信通知运营专员,订单号:{}的订单已创建!",orderEvent.getOrderNum());
    }
}
