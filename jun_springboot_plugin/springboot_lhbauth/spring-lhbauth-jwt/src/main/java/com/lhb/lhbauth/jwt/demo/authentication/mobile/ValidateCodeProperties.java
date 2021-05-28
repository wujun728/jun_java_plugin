package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import lombok.Data;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:48
 */
@Data
public class ValidateCodeProperties {

    private SmsCodeProperties sms = new SmsCodeProperties();
}
