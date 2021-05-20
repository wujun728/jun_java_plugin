package com.monkeyk.sos.infrastructure;

import com.monkeyk.sos.web.context.SOSContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
