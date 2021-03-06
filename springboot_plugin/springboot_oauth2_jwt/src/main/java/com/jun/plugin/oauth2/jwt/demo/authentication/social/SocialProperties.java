package com.jun.plugin.oauth2.jwt.demo.authentication.social;

import com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.QQProperties;
import com.jun.plugin.oauth2.jwt.demo.authentication.social.wexin.WeixinProperties;

import lombok.Data;

/**
 * @author Wujun
 * @description
 * @date 2019/1/3 0003 10:57
 */
@Data
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    private String filterProcessesUrl = "/auth";

    private WeixinProperties weixin = new WeixinProperties();
}
