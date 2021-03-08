package com.zh.springbootmultidatasource.service;

import com.zh.springbootmultidatasource.model.test1.Order;

/**
 * @author Wujun
 * @date 2019/6/6
 */
public interface OrderService {

    Order findByProductId(Integer productId);

    void save(Integer productId, Integer userId);
}
