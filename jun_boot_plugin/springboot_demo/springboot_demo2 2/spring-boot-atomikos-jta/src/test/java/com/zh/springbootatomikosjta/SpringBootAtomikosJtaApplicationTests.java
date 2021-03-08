package com.zh.springbootatomikosjta;

import com.zh.springbootatomikosjta.model.test1.Order;
import com.zh.springbootatomikosjta.model.test2.Stock;
import com.zh.springbootatomikosjta.service.BookProductService;
import com.zh.springbootatomikosjta.service.OrderService;
import com.zh.springbootatomikosjta.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAtomikosJtaApplicationTests {

    @Autowired
    private BookProductService bookProductService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @Test
    public void bookProductTest() {
        Integer productId = 1;
        Integer userId = 1;
        Order order = this.orderService.findByProductId(productId);
        log.info(Optional.ofNullable(order).map(e -> e.toString()).orElse(null));
        Stock stock = this.stockService.findByProductId(productId);
        log.info(Optional.ofNullable(stock).map(e -> e.toString()).orElse(null));
        this.bookProductService.bookProduct(productId,userId);
        order = this.orderService.findByProductId(productId);
        log.info(Optional.ofNullable(order).map(e -> e.toString()).orElse(null));
        stock = this.stockService.findByProductId(productId);
        log.info(Optional.ofNullable(stock).map(e -> e.toString()).orElse(null));
    }

}
