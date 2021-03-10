package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import com.lhb.lhbauth.jwt.demo.service.MyUserDetailsServiceImpl;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:34
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private MyUserDetailsServiceImpl myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SmsCodeAuthenticationToken
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        //校验手机号
        UserDetails user = myUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //这时候已经认证成功了
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //该SmsCodeAuthenticationProvider智支持SmsCodeAuthenticationToken的token认证
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
