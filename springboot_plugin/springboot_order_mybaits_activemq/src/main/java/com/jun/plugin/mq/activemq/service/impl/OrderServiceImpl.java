package com.jun.plugin.mq.activemq.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import com.jun.plugin.mq.activemq.entity.Order;
import com.jun.plugin.mq.activemq.entity.OrderItem;
import com.jun.plugin.mq.activemq.mapper.OrderItemMapper;
import com.jun.plugin.mq.activemq.mapper.OrderMapper;
import com.jun.plugin.mq.activemq.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Order 表数据服务层接口实现类
 *
 */
@Service
public class OrderServiceImpl extends CommonServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public boolean insert(Order order, List<OrderItem> orderitems) {
        try {
            orderMapper.insert(order);
            orderItemMapper.insertBatch(orderitems);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}