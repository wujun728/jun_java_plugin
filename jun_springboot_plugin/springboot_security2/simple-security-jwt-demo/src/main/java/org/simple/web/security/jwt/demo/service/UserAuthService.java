package org.simple.web.security.jwt.demo.service;

import org.simple.web.security.jwt.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 项目名称：web-security-jwt
 * 类名称：UserAuthService
 * 类描述：UserAuthService
 * 创建时间：2018/9/13
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Component
public class UserAuthService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, "{md5}" + new MessageDigestPasswordEncoder("MD5").encode(username));
    }

}
