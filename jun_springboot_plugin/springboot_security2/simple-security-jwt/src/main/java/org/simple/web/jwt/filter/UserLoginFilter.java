package org.simple.web.jwt.filter;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目名称：web-security-jwt
 * 类名称：UserLoginFilter
 * 类描述：UserLoginFilter
 * 创建时间：2018/9/12
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_USERNAME_PARAMETER = "username";

    private static final String DEFAULT_PASSWORD_PARAMETER = "password";

    public UserLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/auth/token", HttpMethod.POST.name()));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                obtainUsername(request), obtainPassword(request)
        ));
    }

    private String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter(DEFAULT_USERNAME_PARAMETER);
        if (username == null) {
            username = "";
        }
        return username.trim();
    }

    private String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter(DEFAULT_PASSWORD_PARAMETER);
        if (password == null) {
            password = "";
        }
        return password;
    }

}
