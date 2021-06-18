package com.lhb.lhbauth.jwt.demo.service;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:50
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
