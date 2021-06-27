package com.alibaba.boot.dubbo.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by wuyu on 2017/5/7.
 */
public class JsonRpcUtil {

    public static JsonNode createSuccessResponse(ObjectMapper objectMapper,String jsonRpc, String id, Object result) throws JsonProcessingException {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("jsonrpc", jsonRpc == null ? "2.0" : jsonRpc);
        response.put("id", id);
        response.put("result", objectMapper.writeValueAsString(result));
        return response;
    }

    public static JsonNode createErrorResponse(ObjectMapper objectMapper,String jsonRpc, String id, int code, String message, Object data) throws JsonProcessingException {
        ObjectNode error = objectMapper.createObjectNode();
        ObjectNode response = objectMapper.createObjectNode();
        error.put("code", code);
        error.put("message", message);
        if (data != null) {
            error.put("data", objectMapper.writeValueAsString(data));
        }
        response.put("id", id);
        response.put("jsonrpc", jsonRpc == null ? "2.0" : jsonRpc);
        response.put("error", objectMapper.writeValueAsString(error));
        return response;
    }
}
