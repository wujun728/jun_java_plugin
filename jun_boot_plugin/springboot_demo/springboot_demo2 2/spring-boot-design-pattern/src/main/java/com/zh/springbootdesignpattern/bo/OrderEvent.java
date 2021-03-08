package com.zh.springbootdesignpattern.bo;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 订单事件类
 * @author Wujun
 * @date 2019/7/18
 */
@Data
public class OrderEvent extends ApplicationEvent {

    private String orderNum;

    public OrderEvent(Object source,String orderNum) {
        super(source);
        this.orderNum = orderNum;
    }
}
