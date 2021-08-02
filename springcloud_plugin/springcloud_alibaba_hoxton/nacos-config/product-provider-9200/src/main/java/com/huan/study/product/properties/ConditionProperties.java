package com.huan.study.product.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author huan.fu 2020/11/18 - 20:49
 */
@ConditionalOnProperty(prefix = "condition", name = "enabled", havingValue = "true")
@Slf4j
@Component
// @RefreshScope
public class ConditionProperties implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("测试 @ConditionalOnProperty 是否生效");
    }
}
