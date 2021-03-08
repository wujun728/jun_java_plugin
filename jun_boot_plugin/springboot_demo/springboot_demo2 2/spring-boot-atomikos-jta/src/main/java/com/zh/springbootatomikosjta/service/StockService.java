package com.zh.springbootatomikosjta.service;


import com.zh.springbootatomikosjta.model.test2.Stock;

/**
 * @author Wujun
 * @date 2019/6/6
 */
public interface StockService {

    Stock findByProductId(Integer productId);

    void decrementByProductId(Integer productId);
}
