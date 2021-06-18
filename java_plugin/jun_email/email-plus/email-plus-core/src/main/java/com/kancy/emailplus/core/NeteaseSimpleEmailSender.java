package com.kancy.emailplus.core;

/**
 * 网易
 * NeteaseSimpleEmailSender
 *
 * @author Wujun
 * @date 2020/2/20 1:59
 */
public class NeteaseSimpleEmailSender extends SimpleEmailSender {
    public NeteaseSimpleEmailSender() {
        setHost("smtp.163.com");
    }

    public NeteaseSimpleEmailSender(String username, String password) {
        this();
        setUsername(username);
        setPassword(password);
    }
}
