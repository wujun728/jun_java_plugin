package com.demo.weixin.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信消息类型
 */
public enum MessageType {

    TEXT("text"), // 文本
    IMAGE("image"), // 图片
    VOICE("voice"),// 语音
    VIDEO("video"), // 视频
    NEWS("news"), // 图文
    FILE("file"), // 文件
    TEXT_CARD("textcard"), // 文本卡片
    MP_NEWS("mpnews"); // 图文消息(mpnews)

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    public static MessageType getTypeByValue(String typeValue) {
        if (StringUtils.isBlank(typeValue)) {
            return null;
        }
        MessageType[] enumValues = MessageType.values();
        for (MessageType messageType : enumValues) {
            if (typeValue.equals(messageType.value)) {
                return messageType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
