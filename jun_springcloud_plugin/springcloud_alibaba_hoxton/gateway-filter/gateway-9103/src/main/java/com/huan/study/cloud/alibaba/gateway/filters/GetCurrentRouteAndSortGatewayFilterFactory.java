package com.huan.study.cloud.alibaba.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author huan.fu 2020/11/15 - 13:16
 */
@Component
@Slf4j
public class GetCurrentRouteAndSortGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> implements Ordered {
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            // 1、获取当前匹配上的路由
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String routeId = route.getId();
            Map<String, Object> metadata = route.getMetadata();
            log.info("routeId:[" + routeId + "]" + "metadata:[" + metadata + "]");

            // 2、过滤器执行的顺序
            log.info("过滤器执行的顺序,参考:[org.springframework.cloud.gateway.handler.FilteringWebHandler.handle]方法");

            return chain.filter(exchange);
        };
    }

    /**
     * 指定过滤器的顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -2;
    }
}
