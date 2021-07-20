package com.jun.plugin.mq.activemq.service;

import com.baomidou.framework.service.ICommonService;
import com.jun.plugin.mq.activemq.entity.OrderItem;

import java.util.List;

/**
 *
 * OrderItem 表数据服务层接口
 *
 */
public interface OrderItemService extends ICommonService<OrderItem> {

    public List<OrderItem> selectByOrderId(String orderId);
}