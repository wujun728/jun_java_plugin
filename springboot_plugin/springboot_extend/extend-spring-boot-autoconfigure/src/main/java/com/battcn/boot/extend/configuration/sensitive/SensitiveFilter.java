package com.battcn.boot.extend.configuration.sensitive;


import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Levin
 * @since 2018/12/25 0025
 */
public class SensitiveFilter extends OncePerRequestFilter {

    private SensitiveProperties sensitiveProperties;

    public SensitiveFilter(SensitiveProperties sensitiveProperties) {
        this.sensitiveProperties = sensitiveProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final SensitiveRequestWrapper requestWrapper = new SensitiveRequestWrapper(request, sensitiveProperties);
        filterChain.doFilter(requestWrapper, response);
    }

}
