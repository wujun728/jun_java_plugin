package com.huan.study.cloud.alibaba.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author huan.fu 2020/11/1 - 22:26
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        log.info("进入了全局过滤器,path:[{}]", path);

        // 修改 request
        ServerHttpRequest request = exchange.getRequest().mutate().header("x-token", "123456").build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 指定全局过滤器执行的顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
