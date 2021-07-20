package com.jun.plugin.mq.activemq.service;

import com.baomidou.framework.service.ICommonService;
import com.jun.plugin.mq.activemq.entity.Order;
import com.jun.plugin.mq.activemq.entity.OrderItem;

import java.util.List;

/**
 *
 * Order 表数据服务层接口
 *
 */
public interface OrderService extends ICommonService<Order> {

    public boolean insert(Order order, List<OrderItem> orderitems);
}