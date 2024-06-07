package com.jun.plugin.groovy.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jun.plugin.groovy.mapping.http.RequestMappingExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MappingRegisterUtil {

//    @Resource
//    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public static synchronized void registerMapping(String method, String path, String scriptType) throws NoSuchMethodException {
        if ("Code".equals(scriptType)) {
            return;
        }
        String pattern = path;
        if (StringUtils.isEmpty(pattern) || pattern.startsWith("TEMP-")) {
            return;
        }
        RequestMappingInfo mappingInfo = getRequestMappingInfo(pattern, method);
        if (mappingInfo != null) {
            return;
        }
        log.debug("Mapped [{}]{}", method, pattern);
        if(!StringUtils.isEmpty(method)) {
            List<RequestMethod> methods = Lists.newArrayList();
            if(StrUtil.containsIgnoreCase("get",method)){
                methods.add(RequestMethod.GET);
            }
            if(StrUtil.containsIgnoreCase("post",method)){
                methods.add(RequestMethod.POST);
            }
            if(StrUtil.containsIgnoreCase("delete",method)){
                methods.add(RequestMethod.DELETE);
            }
            if(StrUtil.containsIgnoreCase("put",method)){
                methods.add(RequestMethod.PUT);
            }
            if(CollectionUtils.isEmpty(methods)){
                mappingInfo = RequestMappingInfo.paths(pattern).build();
            }else {
                mappingInfo = RequestMappingInfo.paths(pattern).methods(ArrayUtil.toArray(methods,RequestMethod.class)).build();
            }
        }else {
            mappingInfo = RequestMappingInfo.paths(pattern).build();
        }
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");
        RequestMappingExecutor mappingFactory = SpringUtil.getBean(RequestMappingExecutor.class);
        Method targetMethod = RequestMappingExecutor.class.getDeclaredMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
        requestMappingHandlerMapping.registerMapping(mappingInfo, mappingFactory, targetMethod);
    }

//    @Resource
//    @Lazy
//    private static RequestMappingExecutor mappingFactory;

    public static synchronized void unregisterMapping(String method, String path, String scriptType) {
        if ("Code".equals(scriptType)) {
            return;
        }
        String pattern = path;

        if (StringUtils.isEmpty(pattern) || pattern.startsWith("TEMP-")) {
            return;
        }
        RequestMappingInfo mappingInfo = getRequestMappingInfo(pattern, method);
        if (mappingInfo == null) {
            return;
        }
        log.info("Cancel Mapping [{}]{}", method==null?"":method, pattern);
        if(!StringUtils.isEmpty(method)) {
            List<RequestMethod> methods = Lists.newArrayList();
            if(StrUtil.containsIgnoreCase("get",method)){
                methods.add(RequestMethod.GET);
            }
            if(StrUtil.containsIgnoreCase("post",method)){
                methods.add(RequestMethod.POST);
            }
            if(StrUtil.containsIgnoreCase("delete",method)){
                methods.add(RequestMethod.DELETE);
            }
            if(StrUtil.containsIgnoreCase("put",method)){
                methods.add(RequestMethod.PUT);
            }
            if(CollectionUtils.isEmpty(methods)){
                mappingInfo = RequestMappingInfo.paths(pattern).build();
            }else {
                mappingInfo = RequestMappingInfo.paths(pattern).methods(ArrayUtil.toArray(methods,RequestMethod.class)).build();
            }
        }else {
            mappingInfo = RequestMappingInfo.paths(pattern).build();
        }
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");
        requestMappingHandlerMapping.unregisterMapping(mappingInfo);
    }

    private static RequestMappingInfo getRequestMappingInfo(String pattern, String method) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            Set<String> patterns = getPatterns(info);
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            if (patterns.contains(pattern) && (methods.isEmpty() || methods.contains(RequestMethod.valueOf(method)))) {
                return info;
            }
        }
        return null;
    }


    public static Set<String> getPatterns(RequestMappingInfo info) {
        return info.getPatternsCondition() == null ? /* info.getPathPatternsCondition().getPatternValues() */null
                : info.getPatternsCondition().getPatterns();
    }

}
