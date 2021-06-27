package com.alibaba.boot.dubbo.websocket;

import com.alibaba.boot.dubbo.generic.GenericServiceConfig;
import com.alibaba.boot.dubbo.generic.JsonRpcUtil;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuyu on 2017/5/7.
 */
public class ServiceHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode jsonNode = invoke(message.getPayload());
        session.sendMessage(new TextMessage(jsonNode.toString()));
    }

    @PostConstruct
    public void afterPropertiesSet() {
        ApplicationContext springContext = ServiceBean.getSpringContext();
        if (springContext == null) {
            return;
        }
        Map<String, ServiceBean> serviceBeanMap = springContext.getBeansOfType(ServiceBean.class);
        for (ServiceBean serviceBean : serviceBeanMap.values()) {
            Object ref = serviceBean.getRef();
            String id = serviceBean.getId();
            Class<?> targetClass = AopUtils.getTargetClass(ref);
            serviceMap.put(targetClass.getName(), ref);
            if (id != null) {
                serviceMap.put(id, ref);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        serviceMap.clear();
    }

    protected JsonNode invoke(String message) throws JsonProcessingException {
        GenericServiceConfig genericServiceConfig = null;
        JsonNode jsonNode;
        try {
            genericServiceConfig = objectMapper.readValue(message, GenericServiceConfig.class);
            String fullName = genericServiceConfig.getMethod();
            String serviceName = fullName.substring(0, fullName.lastIndexOf(".") + 1);
            Object bean = serviceMap.get(serviceName);
            if (bean == null) {
                throw new RpcException("not found service");
            }
            String methodName = fullName.substring(fullName.lastIndexOf("."));
            Method method = ReflectionUtils.findMethod(AopUtils.getTargetClass(bean), methodName);

            JsonNode params = genericServiceConfig.getParams();
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Object[] args = new Object[genericParameterTypes.length];
            for (int i = 0; i < genericParameterTypes.length; i++) {
                JavaType javaType = TypeFactory.defaultInstance().constructType(genericParameterTypes[i]);
                JsonNode param = params.get(i);
                if (param.isNull()) {
                    args[i] = null;
                } else if (String.class.isAssignableFrom(javaType.getRawClass())) {
                    args[i] = param.toString();
                } else {
                    args[i] = objectMapper.convertValue(param, javaType);
                }

            }
            Object result = method.invoke(args);
            jsonNode = JsonRpcUtil.createSuccessResponse(objectMapper, "2.0", genericServiceConfig.getId(), result);
        } catch (Exception e) {
            e.printStackTrace();
            jsonNode = JsonRpcUtil.createErrorResponse(objectMapper, "2.0", genericServiceConfig != null ? genericServiceConfig.getId() : null, 32603, e.getMessage(), null);
        }

        return jsonNode;
    }


}
