package com.zh.springbootatomikosjta.service.impl;

import com.zh.springbootatomikosjta.dao.test1.OrderMapper;
import com.zh.springbootatomikosjta.model.test1.Order;
import com.zh.springbootatomikosjta.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/10
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findByProductId(Integer productId) {
        return this.orderMapper.findByProductId(productId);
    }

    @Override
    public void save(Integer productId, Integer userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setCreateTime(new Date());
        order.setRmk("创建一笔订单");
        this.orderMapper.insertSelective(order);
    }
}
