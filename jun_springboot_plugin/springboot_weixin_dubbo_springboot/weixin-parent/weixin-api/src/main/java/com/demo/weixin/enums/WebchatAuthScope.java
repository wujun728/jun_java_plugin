package com.demo.weixin.enums;

/**
 * 微信公众号授权scope 应用授权作用域。
 */
public enum WebchatAuthScope {

    SNSAPI_BASE("snsapi_base"), // 静默授权，可获取成员的基础信息
    SNSAPI_USERINFO("snsapi_userinfo"), // 静默授权，可获取成员的详细信息，但不包含手机、邮箱；
    SNSAPI_PRIVATEINFO("snsapi_privateinfo"); // 手动授权，可获取成员的详细信息，包含手机、邮箱。

    private String weixinValue; // 传给微信的值

    WebchatAuthScope(String weixinValue) {
        this.weixinValue = weixinValue;
    }

    public static WebchatAuthScope getScopeByValue(String scopeValue) {
        if (scopeValue == null) {
            return null;
        }
        WebchatAuthScope[] enumValues = WebchatAuthScope.values();
        for (WebchatAuthScope webchatAuthScope : enumValues) {
            if (scopeValue.equals(webchatAuthScope.getWeixinValue())) {
                return webchatAuthScope;
            }
        }
        return null;
    }

    public String getWeixinValue() {
        return weixinValue;
    }
}
