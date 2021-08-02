package com.huan.study.cloud.alibaba.gateway.filters;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huan.fu 2020/11/1 - 22:38
 */
@Component
@Slf4j
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenGatewayFilterFactory.Config> {

    public TokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Lists.newArrayList("tokenName");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("每次路由匹配到会执行");
            String tokenName = config.getTokenName();
            log.info("从配置文件中获取到的 tokenName 的值=[{}].", tokenName);
            String value = exchange.getRequest().getQueryParams().getFirst(tokenName);
            log.info("从请求中获取到的token value 是:[{}]", value);
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        private String tokenName;
    }
}
