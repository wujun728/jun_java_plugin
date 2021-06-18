package org.tdcg.service;

import com.baomidou.framework.service.ICommonService;
import org.tdcg.entity.Order;
import org.tdcg.entity.OrderItem;

import java.util.List;

/**
 *
 * Order 表数据服务层接口
 *
 */
public interface OrderService extends ICommonService<Order> {

    public boolean insert(Order order, List<OrderItem> orderitems);
}