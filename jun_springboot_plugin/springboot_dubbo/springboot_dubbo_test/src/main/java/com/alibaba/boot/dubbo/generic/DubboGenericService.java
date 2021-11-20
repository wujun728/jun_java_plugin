package com.alibaba.boot.dubbo.generic;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuyu on 2017/4/28.
 */
public class DubboGenericService implements ApplicationContextAware {

    private Map<String, GenericService> genericServiceMap = new ConcurrentHashMap<>();

    private Set<ReferenceConfig<GenericService>> referenceBeans = new ConcurrentHashSet<>();

    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    public JsonNode proxy(@RequestBody GenericServiceConfig genericServiceConfig) throws IOException {
        String jsonrpc = genericServiceConfig.getJsonrpc();
        String id = genericServiceConfig.getId();
        if (StringUtils.isBlank(genericServiceConfig.getMethod())) {
            return JsonRpcUtil.createErrorResponse(objectMapper, jsonrpc, id, 32601, "method not find!", null);
        }
        if (genericServiceConfig.getParams() != null && !genericServiceConfig.getParams().isArray()) {
            return JsonRpcUtil.createErrorResponse(objectMapper, jsonrpc, id, 32602, "parameters must be array!", null);
        }
        Object result;
        try {
            String[] method = genericServiceConfig.getMethod().split("[.]");
            GenericService genericService = genericService(genericServiceConfig);
            JsonNode jsonArray = genericServiceConfig.getParams();
            if (jsonArray.size() > 0) {
                Object[] params = new Object[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonNode jsonNode = jsonArray.get(i);
                    if (jsonNode.isNull()) {
                        params[i] = null;
                    } else if (jsonNode.canConvertToInt()) {
                        params[i] = jsonNode.asInt();
                    } else if (jsonNode.canConvertToLong()) {
                        params[i] = jsonNode.asLong();
                    } else if (jsonNode.isBoolean()) {
                        params[i] = jsonNode.asBoolean();
                    } else if (jsonNode.isArray()) {
                        params[i] = objectMapper.readValue(jsonNode.toString(), ArrayList.class);
                    } else if (jsonNode.isPojo()) {
                        params[i] = objectMapper.readValue(jsonNode.toString(), LinkedHashMap.class);
                    } else if (jsonNode.isObject()) {
                        params[i] = objectMapper.readValue(jsonNode.toString(), LinkedHashMap.class);
                    } else if (jsonNode.isBigDecimal()) {
                        params[i] = new BigDecimal(jsonNode.toString());
                    } else if (jsonNode.isBigInteger()) {
                        params[i] = new BigInteger(jsonNode.toString());
                    } else if (jsonNode.isTextual()) {
                        params[i] = jsonNode.toString();
                    }
                }
                result = genericService.$invoke(method[method.length - 1], genericServiceConfig.getParamsType(), params);
            } else {
                result = genericService.$invoke(method[method.length - 1], new String[]{}, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonRpcUtil.createErrorResponse(objectMapper, genericServiceConfig.getJsonrpc(), genericServiceConfig.getId(), 32600, e.getMessage(), null);
        }

        return JsonRpcUtil.createSuccessResponse(objectMapper, genericServiceConfig.getJsonrpc(), genericServiceConfig.getId(), result);
    }


    protected GenericService genericService(GenericServiceConfig config) throws Exception {
        String key = sliceKey(config);
        GenericService genericService = genericServiceMap.get(key);
        if (genericService != null) {
            return genericService;
        }
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>(); // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        String method = config.getMethod();
        String service = method.substring(0, method.lastIndexOf("."));
        reference.setInterface(service);
        reference.setApplication(applicationContext.getBean(ApplicationConfig.class));
        reference.setRegistries(new ArrayList<RegistryConfig>(applicationContext.getBeansOfType(RegistryConfig.class).values()));
        if (config.getVersion() != null && !config.getVersion().equals("0.0.0")) {
            reference.setVersion(config.getVersion());
        }
        if (config.getGroup() != null && (!config.getGroup().equalsIgnoreCase("defaultGroup"))) {
            reference.setGroup(config.getGroup());
        }
        reference.setGeneric(true); // 声明为泛化接口
        reference.setProtocol("dubbo");
        genericService = reference.get();
        referenceBeans.add(reference);
        genericServiceMap.put(key, genericService);
        return genericService; // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
    }

    private String sliceKey(GenericServiceConfig config) {
        String service = config.getMethod().substring(0, config.getMethod().lastIndexOf("."));
        return "/" + config.getGroup() + "/" + config.getVersion() + "/" + service;
    }


    @PreDestroy
    public void destroy() {
        for (ReferenceConfig<GenericService> referenceBean : referenceBeans) {
            referenceBean.destroy();
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
