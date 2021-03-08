package com.demo.weixin.entity.message;

import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 * @description 文本消息
 * @date 2017/7/31
 * @since 1.0
 */
public class TextMessage extends BasicMessage {
    private String content; // 必填。消息内容，最长不超过2048个字节
    private boolean safe; // 表示是否是保密消息，0表示否，1表示是，默认0

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (MessageType.TEXT != messageType || StringUtils.isBlank(content)) {
            throw new WeixinException(-1, "text的消息类型错误，或消息内容为空");
        }
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("content", content);

        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("text", contentMap);
        paramMap.put("safe", safe ? "1" : "0");
        return paramMap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
