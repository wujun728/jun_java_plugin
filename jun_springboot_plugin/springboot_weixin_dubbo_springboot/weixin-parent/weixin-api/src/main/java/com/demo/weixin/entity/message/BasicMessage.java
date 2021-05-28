package com.demo.weixin.entity.message;

import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @description 消息的基本属性
 * @date 2017/7/31
 * @since 1.0
 */
public class BasicMessage {

    public MessageType messageType; // 必填。消息类型
    public Integer agentId; // 必填。企业应用的id，整型。可在应用的设置页面查看
    public String toUser;// 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
    public String toParty; // 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
    public String toTag;// 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数


    public Map<String, Object> getParamMap() throws WeixinException {
        // 验证必要的参数
        if (messageType == null || agentId == null) {
            throw new WeixinException(-1, "BasicSendMessageRequestHandler的messageType 或 agentId参数缺失");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("msgtype", messageType.getValue());
        paramMap.put("agentid", agentId.toString());

        this.addValueFromString("touser", toUser, paramMap);
        this.addValueFromString("toparty", toParty, paramMap);
        this.addValueFromString("totag", toTag, paramMap);

        return paramMap;
    }

    public void addValueFromString(String key, String value, Map<String, Object> paramMap) {
        if (StringUtils.isAnyBlank(key, value) || paramMap == null) {
            return;
        }
        paramMap.put(key, value);
    }

    public void addValueFromList(String key, List value, Map<String, Object> paramMap) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(value) || paramMap == null) {
            return;
        }
        paramMap.put(key, value);
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }
}
