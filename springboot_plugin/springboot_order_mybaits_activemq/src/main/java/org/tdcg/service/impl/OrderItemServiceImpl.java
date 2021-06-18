package org.tdcg.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tdcg.entity.OrderItem;
import org.tdcg.mapper.OrderItemMapper;
import org.tdcg.service.OrderItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * OrderItem 表数据服务层接口实现类
 *
 */
@Service
public class OrderItemServiceImpl extends CommonServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> selectByOrderId(String orderId) {
        Map<String, Object> cm = new HashMap<>();
        cm.put("order_id", orderId);
        return orderItemMapper.selectByMap(cm);
    }
}