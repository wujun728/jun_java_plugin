package com.jun.plugin.oauth.server.infrastructure;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.jun.plugin.oauth.server.web.context.SOSContextHolder;

/**
 * 2016/3/25
 *
 * @author Wujun
 */
public abstract class PasswordHandler {


//    private PasswordEncoder passwordEncoder = SOSContextHolder.getBean(PasswordEncoder.class);


    private PasswordHandler() {
    }


    public static String encode(String password) {
        PasswordEncoder passwordEncoder = SOSContextHolder.getBean(PasswordEncoder.class);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
