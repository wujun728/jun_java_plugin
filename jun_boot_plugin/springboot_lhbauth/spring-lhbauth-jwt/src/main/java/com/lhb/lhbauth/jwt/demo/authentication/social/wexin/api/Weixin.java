package com.lhb.lhbauth.jwt.demo.authentication.social.wexin.api;

import com.lhb.lhbauth.jwt.demo.authentication.social.wexin.model.WeixinUserInfo;

/**
 * @author Wujun
 * @description
 * @date 2019/1/4 0004 9:49
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);
}
