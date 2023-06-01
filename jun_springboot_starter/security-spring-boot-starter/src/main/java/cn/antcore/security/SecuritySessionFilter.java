package cn.antcore.security;

import cn.antcore.security.request.HttpSecurityServletRequestWrapper;
import cn.antcore.security.session.SessionStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ant Security过滤器
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class SecuritySessionFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy;

    public SecuritySessionFilter(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new HttpSecurityServletRequestWrapper(httpServletRequest, httpServletResponse, sessionStrategy), httpServletResponse);
    }
}
