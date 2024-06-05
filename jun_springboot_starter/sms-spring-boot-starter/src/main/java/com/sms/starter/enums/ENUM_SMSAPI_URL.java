package com.sms.starter.enums;

import lombok.Getter;

/**
 * 云之讯短信请求API枚举
 *
 * @Author Sans
 * @CreateTime 2019/4/20
 * @attention
 */
@Getter
public enum ENUM_SMSAPI_URL {
    SENDSMS("https://open.ucpaas.com/ol/sms/sendsms"),
    SENDBATCHSMS("https://open.ucpaas.com/ol/sms/sendsms_batch");
    private String url;
    ENUM_SMSAPI_URL(String url) {
        this.url = url;
    }
}
