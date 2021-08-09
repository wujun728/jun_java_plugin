package com.huan.study.cloud.alibaba.product.controller;

import com.huan.study.cloud.alibaba.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huan.fu 2020/10/23 - 14:23
 */
@RestController
@Slf4j
@RequestMapping("product")
public class ProductController {

    private static final Map<Integer, Product> PRODUCT_MAP = new HashMap<>(16);

    static {
        PRODUCT_MAP.put(1, Product.builder().id(1).name("苹果").price(20L).craeteTime(new Date()).build());
        PRODUCT_MAP.put(2, Product.builder().id(2).name("栗子").price(30L).craeteTime(new Date()).build());
        PRODUCT_MAP.put(3, Product.builder().id(3).name("橘子").price(50L).craeteTime(new Date()).build());
    }

    @GetMapping("findAll")
    public Collection<Product> findAll() {
        log.info("查询所有商品");
        return PRODUCT_MAP.values();
    }

    @GetMapping("findOne")
    public Product findOne(@RequestParam("id") Integer id) {
        log.info("获取id:[{}]的商品.", id);
        return PRODUCT_MAP.get(id);
    }

    @GetMapping("findOne/{productId}")
    public Product findOneProduct(@PathVariable("productId") Integer productId) {
        log.info("获取productId:[{}]的商品.", productId);
        return PRODUCT_MAP.get(productId);
    }
}
