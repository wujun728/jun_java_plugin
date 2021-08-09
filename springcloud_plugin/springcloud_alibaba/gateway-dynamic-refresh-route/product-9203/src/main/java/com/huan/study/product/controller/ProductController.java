package com.huan.study.product.controller;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * product controller
 *
 * @author huan.fu 2021/6/17 - 下午1:58
 */
@RestController
public class ProductController {

    @GetMapping("findAllProduct")
    public List<String> findAllProduct() {
        return Lists.newArrayList("苹果", "橘子", "香蕉");
    }
}
