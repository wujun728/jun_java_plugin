/**
 * SystemTagAuthHandlerInterceptor.java
 * Created at 2016-11-16
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.exception.AuthException;
import org.itkk.udf.core.exception.PermissionException;
import org.itkk.udf.rms.meta.ApplicationMeta;
import org.itkk.udf.rms.meta.ServiceMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述 : SystemTagAuthHandlerInterceptor
 *
 * @author wangkang
 */
@Slf4j
public class RmsAuthHandlerInterceptor implements HandlerInterceptor {

    /**
     * 描述 : 环境标识
     */
    private static final String DEV_PROFILES = "dev";

    /**
     * 描述 : 配置
     */
    @Autowired
    private RmsProperties rmsProperties;

    /**
     * 描述 : 环境变量
     */
    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) { //NOSONAR
        //获取请求方法
        String method = request.getMethod();
        //如果是options请求,则直接返回true (处理跨域情况)
        if (HttpMethod.OPTIONS.equals(HttpMethod.resolve(method))) {
            return true;
        }
        //获得当前环境
        String profilesActive = env.getProperty("spring.profiles.active");
        //获取请求地址
        String url = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        //获取认证信息(应用名称)
        String rmsApplicationName = request.getHeader(Constant.HEADER_RMS_APPLICATION_NAME_CODE);
        if (StringUtils.isBlank(rmsApplicationName)) {
            rmsApplicationName = request.getParameter(Constant.HEADER_RMS_APPLICATION_NAME_CODE);
        }
        //获取认证信息(sign)
        String rmsSign = request.getHeader(Constant.HEADER_RMS_SIGN_CODE);
        if (StringUtils.isBlank(rmsSign)) {
            rmsSign = request.getParameter(Constant.HEADER_RMS_SIGN_CODE);
        }
        //获取认证信息(服务代码)
        String rmsServiceCode = request.getHeader(Constant.HEADER_SERVICE_CODE_CODE);
        if (StringUtils.isBlank(rmsServiceCode)) {
            rmsServiceCode = request.getParameter(Constant.HEADER_SERVICE_CODE_CODE);
        }
        //日志
        log.debug("profiles.active:{},rmsApplicationName:{},rmsSign:{},rmsServiceCode:{},url:{},method:{}", profilesActive, rmsApplicationName, rmsSign, rmsServiceCode, url, method);
        //判断systemTag是否有效
        if (!this.rmsProperties.getApplication().containsKey(rmsApplicationName)) {
            throw new AuthException("unrecognized systemTag:" + rmsApplicationName);
        }
        //获得应用元数据
        ApplicationMeta applicationMeta = rmsProperties.getApplication().get(rmsApplicationName);
        //判断环境(开发环境无需校验sign)
        if (!DEV_PROFILES.equals(profilesActive)) {
            //判断是否缺少认证信息
            if (StringUtils.isBlank(rmsSign)) {
                throw new AuthException("missing required authentication parameters (rmsSign)");
            }
            //获得secret
            String secret = applicationMeta.getSecret();
            //计算sign
            String sign = Constant.sign(rmsApplicationName, secret);
            //比较sign
            if (!rmsSign.equals(sign)) {
                throw new AuthException("sign Validation failed");
            }
        }
        //判断是否有调用所有服务的权限
        if (!applicationMeta.getAll()) {
            //判断是否有服务代码
            if (StringUtils.isBlank(rmsServiceCode)) {
                throw new AuthException("missing required authentication parameters (rmsServiceCode)");
            }
            //判断是否禁止调用所有服务权限
            if (applicationMeta.getDisabled()) {
                throw new PermissionException(rmsApplicationName + " is disabled");
            }
            //判断是否有调用该服务的权限
            if (applicationMeta.getPurview().indexOf(rmsServiceCode) == -1) {
                throw new PermissionException("no access to this servoceCode : " + rmsServiceCode);
            }
            //判断服务元数据是否存在
            if (!rmsProperties.getService().containsKey(rmsServiceCode)) {
                throw new PermissionException("service code not exist");
            }
            //获得服务元数据
            ServiceMeta serviceMeta = rmsProperties.getService().get(rmsServiceCode);
            //比较url和method的有效性
            if (!serviceMeta.getUri().equals(url) || !serviceMeta.getMethod().equals(method)) {
                throw new PermissionException("url and method verification error");
            }
        }
        //返回
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.debug("SystemTagAuthHandlerInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.debug("SystemTagAuthHandlerInterceptor.afterCompletion");
    }

}
