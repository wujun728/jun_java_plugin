package io.github.wujun728.oauth2.common.service.impl;

import io.github.wujun728.oauth2.common.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义 TokenExtractor
 *
 */
@ConditionalOnClass(HttpServletRequest.class)
@Component
public class CustomBearerTokenExtractor extends BearerTokenExtractor {
    @Resource
    private SecurityProperties securityProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 解决只要请求携带access_token，排除鉴权的url依然会被拦截
     */
    @Override
    public Authentication extract(HttpServletRequest request) {
        //判断当前请求为排除鉴权的url时，直接返回null
        for (String url : securityProperties.getIgnore().getUrls()) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                return null;
            }
        }
        return super.extract(request);
    }
}
