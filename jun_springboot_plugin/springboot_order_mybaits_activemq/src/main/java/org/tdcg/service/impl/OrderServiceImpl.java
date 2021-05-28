package org.tdcg.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tdcg.entity.Order;
import org.tdcg.entity.OrderItem;
import org.tdcg.mapper.OrderItemMapper;
import org.tdcg.mapper.OrderMapper;
import org.tdcg.service.OrderService;

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