package com.jun.plugin.oauth2.jwt.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.jun.plugin.oauth2.jwt.demo.service.SmsCodeSender;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:50
 */
@Slf4j
@Service
public class SmsCodeSenderImpl implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        log.info("向手机:"+mobile+"发送短信验证码:"+code);
    }
}
