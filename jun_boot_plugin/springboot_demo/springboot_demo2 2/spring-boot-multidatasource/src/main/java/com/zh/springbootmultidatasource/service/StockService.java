package com.zh.springbootmultidatasource.service;

import com.zh.springbootmultidatasource.model.test2.Stock;

/**
 * @author Wujun
 * @date 2019/6/6
 */
public interface StockService {

    Stock findByProductId(Integer productId);

    void decrementByProductId(Integer productId);
}
