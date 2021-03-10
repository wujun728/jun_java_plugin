package com.demo.weixin.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信授权使用客户端类型 - 对应微信开放平台的应用
 */
public enum ClientType {
    WEB(0),
    PUBLIC(1);

    private final int value;

    private ClientType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static ClientType findByValue(int value) {
        switch (value) {
            case 0:
                return WEB;
            case 1:
                return PUBLIC;
            default:
                return null;
        }
    }

    public static ClientType findByLowerCaseValue(String lowerCaseValue) {
        if (StringUtils.isBlank(lowerCaseValue)) {
            return null;
        }
        switch (lowerCaseValue) {
            case "web":
                return ClientType.WEB;
            case "public":
                return ClientType.PUBLIC;
            default:
                return null;
        }
    }
}
