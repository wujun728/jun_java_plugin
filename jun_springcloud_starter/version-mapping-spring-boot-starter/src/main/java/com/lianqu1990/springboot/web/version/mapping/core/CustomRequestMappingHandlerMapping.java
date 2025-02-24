package com.lianqu1990.springboot.web.version.mapping.core;

import com.lianqu1990.springboot.web.version.mapping.annotation.ApiVersion;
import com.lianqu1990.springboot.web.version.mapping.annotation.ClientVersion;
import com.lianqu1990.springboot.web.version.mapping.condition.ClientVersionRequestCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author hanchao
 * @date 2018/3/9 10:49
 */
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    //重写此处，保证读取我们的注解apiversion
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappinginfo = super.getMappingForMethod(method, handlerType);
        if(mappinginfo != null){
            RequestMappingInfo apiVersionMappingInfo = getApiVersionMappingInfo(method, handlerType);
            return apiVersionMappingInfo == null ? mappinginfo : apiVersionMappingInfo.combine(mappinginfo);
        }
        return mappinginfo;
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ClientVersion clientVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, ClientVersion.class);
        return createRequestCondtion(clientVersion);
    }

    //重新定义clientversion的条件匹配
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ClientVersion clientVersion = AnnotatedElementUtils.findMergedAnnotation(method, ClientVersion.class);
        return createRequestCondtion(clientVersion);
    }

    private RequestCondition<?> createRequestCondtion(ClientVersion clientVersion){
        if(clientVersion == null){
            return null;
        }
        if(clientVersion.value() != null && clientVersion.value().length > 0){
            return new ClientVersionRequestCondition(clientVersion.value());
        }
        if(clientVersion.expression() != null && clientVersion.expression().length > 0){
            return new ClientVersionRequestCondition(clientVersion.expression());
        }
        return null;
    }

    private RequestMappingInfo getApiVersionMappingInfo(Method method,Class<?> handlerType){
        //优先查找method
        ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
        if(apiVersion == null || StringUtils.isBlank(apiVersion.value())){
            apiVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType,ApiVersion.class);
        }
        return apiVersion == null || StringUtils.isBlank(apiVersion.value()) ? null : RequestMappingInfo
                .paths(apiVersion.value())
                .build();
    }
}
