package org.tdcg.service;

import com.baomidou.framework.service.ICommonService;
import org.tdcg.entity.OrderItem;

import java.util.List;

/**
 *
 * OrderItem 表数据服务层接口
 *
 */
public interface OrderItemService extends ICommonService<OrderItem> {

    public List<OrderItem> selectByOrderId(String orderId);
}