package com.huan.study.cloud.alibaba.product.controller;

import com.google.common.collect.Lists;
import com.huan.study.cloud.alibaba.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @RequestMapping(value = "findOne", method = {RequestMethod.GET, RequestMethod.POST})
    public Product findOne(@RequestParam("id") Integer id,
                           @RequestParam(value = "username", required = false) String username,
                           HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("x-token");
        List<String> xTokens = Lists.newArrayList();
        while (headers.hasMoreElements()) {
            xTokens.add(headers.nextElement());
        }
        log.info("获取id:[{}]的商品,x-token:[{}],username:[{}]", id, xTokens, username);
        return PRODUCT_MAP.get(id);
    }

    @GetMapping("findOne/{productId}")
    public Product findOneProduct(@PathVariable("productId") Integer productId, HttpServletRequest request) {
        String token = request.getHeader("x-token");
        log.info("获取productId:[{}]的商品,x-token:[{}]", productId, token);
        return PRODUCT_MAP.get(productId);
    }

    @PostMapping("modifyRequestBody")
    public String modifyRequestBody(@RequestBody String body) {
        log.info("此处获取到的值，是经过网关修改之后的");
        return body;
    }
}
