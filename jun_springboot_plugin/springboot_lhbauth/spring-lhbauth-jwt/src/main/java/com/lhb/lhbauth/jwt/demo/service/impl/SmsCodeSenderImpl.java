package com.lhb.lhbauth.jwt.demo.service.impl;

import com.lhb.lhbauth.jwt.demo.service.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
