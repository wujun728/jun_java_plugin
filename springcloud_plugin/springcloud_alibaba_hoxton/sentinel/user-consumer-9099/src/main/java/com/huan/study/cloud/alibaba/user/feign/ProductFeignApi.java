package com.huan.study.cloud.alibaba.user.feign;

import com.huan.study.cloud.alibaba.user.feign.fallback.ProductFeignApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huan.fu 2020/10/25 - 11:55
 */
@FeignClient(name = "product-provider", fallbackFactory = ProductFeignApiFallbackFactory.class)
public interface ProductFeignApi {

    @GetMapping("findAll")
    String findAllProduct();
}
