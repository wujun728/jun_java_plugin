package org.simple.web.jwt.filter;

import org.simple.web.jwt.property.JwtAuthFilterProperty;
import org.simple.web.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目名称：web-web-jwt
 * 类名称：JwtAuthenticationTokenFilter
 * 类描述：JwtAuthenticationTokenFilter jwt认证过滤器
 * 创建时间：2018/4/11 16:40
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilterProperty jwtAuthFilterProperty;

    /**
     * 过滤器逻辑
     *
     * @param request  .
     * @param response .
     * @param chain    .
     * @throws ServletException .
     * @throws IOException      ,
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.jwtAuthFilterProperty.getHeader());
        String tokenHead = this.jwtAuthFilterProperty.getTokenHead();
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            if (jwtService.validateToken(authToken)) {
                String subject = jwtService.getSubjectFromToken(authToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
