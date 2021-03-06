package com.jun.plugin.oauth2.jwt.demo.authentication.mobile;

import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jun.plugin.oauth2.jwt.demo.properties.SecurityProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:57
 */
@Data
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator{

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public ValidateCode createImageCode(HttpServletRequest request, HttpServletResponse response) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());

    }
}
