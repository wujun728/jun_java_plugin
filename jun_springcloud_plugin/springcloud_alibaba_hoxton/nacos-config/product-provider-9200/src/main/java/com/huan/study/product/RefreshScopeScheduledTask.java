package com.huan.study.product;

import com.huan.study.product.properties.ProductProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 如果定时任务的类上增加类 @RefreshScope 注解，那么 nacos 中的配置属性动态修改后，这个定时任务会失效
 *
 * @author huan.fu 2020/11/22 - 12:37
 */
@Component
@Slf4j
@RequiredArgsConstructor
// @RefreshScope
public class RefreshScopeScheduledTask {

    private final ProductProperties productProperties;

    @Scheduled(fixedDelay = 1000)
    public void println01() {
        log.info("println01" + productProperties);
    }
}
