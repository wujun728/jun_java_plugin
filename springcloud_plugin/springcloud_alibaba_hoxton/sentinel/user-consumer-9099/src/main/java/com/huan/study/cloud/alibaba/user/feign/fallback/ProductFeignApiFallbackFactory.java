package com.huan.study.cloud.alibaba.user.feign.fallback;

import com.huan.study.cloud.alibaba.user.feign.ProductFeignApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author huan.fu 2020/10/25 - 11:57
 */
@Slf4j
@Component
public class ProductFeignApiFallbackFactory implements FallbackFactory<ProductFeignApi> {
    @Override
    public ProductFeignApi create(Throwable throwable) {
        return new ProductFeignApi() {
            @Override
            public String findAllProduct() {
                log.warn("出现了异常", throwable);
                return "服务降级或限流了";
            }
        };
    }
}
