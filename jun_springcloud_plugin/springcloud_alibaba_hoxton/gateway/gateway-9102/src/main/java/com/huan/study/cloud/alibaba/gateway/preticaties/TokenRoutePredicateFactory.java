package com.huan.study.cloud.alibaba.gateway.preticaties;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author huan.fu 2020/11/1 - 13:47
 */
@Slf4j
@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {
    public TokenRoutePredicateFactory() {
        super(Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return Lists.newArrayList("token");
    }
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            log.info("判断请求头中是否存在token这个参数");
            String token = exchange.getRequest().getQueryParams().getFirst(config.getToken());
            return StringUtils.isNotBlank(token);
        };
    }
    @Data
    public static class Config {
        private String token;
    }
}
