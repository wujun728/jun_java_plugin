package com.battcn.boot.extend.configuration.xss;


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
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final XssRequestWrapper requestWrapper = new XssRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

}
