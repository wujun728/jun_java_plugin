package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wujun
 * @description
 * @date 2018/11/27 0027 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeProperties {

    private int length = 6;
    private int expireIn = 60;
    private String url = "/user1";
}
