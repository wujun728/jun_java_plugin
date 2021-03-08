package com.zh.springbootatomikosjta.service.impl;

import com.zh.springbootatomikosjta.dao.test2.StockMapper;
import com.zh.springbootatomikosjta.model.test2.Stock;
import com.zh.springbootatomikosjta.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wujun
 * @date 2019/6/10
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public Stock findByProductId(Integer productId) {
        return this.stockMapper.findByProductId(productId);
    }

    @Override
    public void decrementByProductId(Integer productId) {
        this.stockMapper.decrementByProductId(productId);
    }
}
