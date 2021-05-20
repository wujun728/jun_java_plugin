package com.mall.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.annotation.Param;
import com.mall.common.annotation.ParamsType;
import com.mall.common.annotation.ParamsValidate;
import com.mall.common.utils.StrKit;
import io.jboot.component.swagger.ParamType;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数校验拦截器
 * @author Wujun
 * @version 1.0
 * @create_at 2018/12/22 19:31
 */
@Component
public class ParamsValidateInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            ParamsValidate paramsValidate = handlerMethod.getMethod().getAnnotation(ParamsValidate.class);
            if (paramsValidate != null) {
                Param[] params = paramsValidate.params();
                ParamsType paramsType = paramsValidate.paramsType();
                return checkParam(request, response, params, paramsType);
            }
        }
        return true;
    }

    private boolean checkParam(HttpServletRequest request, HttpServletResponse response, Param[] params,
                               ParamsType paramsType) {
        Map dataMap = null;
        boolean isJsonData = false;
        if (paramsType == ParamsType.JSON_DATA) {
            String data = readData(request);
            dataMap = JSONObject.parseObject(data);
            isJsonData = true;
        }
        for (Param param : params) {
            String name = param.name();
            String value = null;
            if (paramsType == ParamsType.FORM_DATA) {
                value = request.getParameter(name);
            } else if (isJsonData && dataMap != null) {
                value = (String)dataMap.get(name);
            }
            if (StrKit.isEmpty(value)) {
                Map resultMap = new HashMap<>();
                resultMap.put("code", param.errorCode());
                resultMap.put("message", param.message());
                renderJson(response, resultMap);
                return false;
            }
        }
        return true;
    }


}
