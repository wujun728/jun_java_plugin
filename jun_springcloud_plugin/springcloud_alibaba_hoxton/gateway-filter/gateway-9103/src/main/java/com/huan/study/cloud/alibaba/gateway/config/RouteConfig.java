package com.huan.study.cloud.alibaba.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huan.fu 2020/11/1 - 20:54
 */
@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-provider", predicateSpec -> predicateSpec.path("/product/modifyRequestBody")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.modifyRequestBody(String.class, Map.class, MediaType.APPLICATION_JSON_VALUE, (exchange, s) -> {
                            Map<String, Object> params = new HashMap<>(16);
                            params.put("old", s);
                            params.put("username", "v_huan");
                            params.put("roles", "ROLE_ADMIN");
                            return Mono.just(params);
                        })).uri("lb://product-provider")).build();
    }
}
