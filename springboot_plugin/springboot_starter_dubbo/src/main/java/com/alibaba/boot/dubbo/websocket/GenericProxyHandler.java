package com.alibaba.boot.dubbo.websocket;

import com.alibaba.boot.dubbo.generic.DubboGenericService;
import com.alibaba.boot.dubbo.generic.GenericServiceConfig;
import com.alibaba.boot.dubbo.generic.JsonRpcUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by wuyu on 2017/5/7.
 */
public class GenericProxyHandler extends TextWebSocketHandler {

    @Autowired
    private DubboGenericService dubboGenericProxyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode jsonNode = invoke(message.getPayload());
        session.sendMessage(new TextMessage(jsonNode.toString()));
    }

    protected JsonNode invoke(String message) throws JsonProcessingException {
        GenericServiceConfig genericServiceConfig = null;
        JsonNode jsonNode;
        try {
            genericServiceConfig = objectMapper.readValue(message, GenericServiceConfig.class);
            jsonNode = dubboGenericProxyService.proxy(genericServiceConfig);
        } catch (Exception e) {
            e.printStackTrace();
            jsonNode = JsonRpcUtil.createErrorResponse(objectMapper,"2.0", genericServiceConfig != null ? genericServiceConfig.getId() : null, 32603, e.getMessage(), null);
        }

        return jsonNode;
    }
}
