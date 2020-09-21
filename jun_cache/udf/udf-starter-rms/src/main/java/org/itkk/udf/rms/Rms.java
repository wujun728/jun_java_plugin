/**
 * Rms.java
 * Created at 2017-04-24
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.exception.PermissionException;
import org.itkk.udf.rms.meta.ApplicationMeta;
import org.itkk.udf.rms.meta.ServiceMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 : 远程服务
 *
 * @author Administrator
 */
@Component
@Slf4j
public class Rms {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    /**
     * 描述 : restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * externalRestTemplate
     */
    @Autowired
    @Qualifier("externalRestTemplate")
    private RestTemplate externalRestTemplate;

    /**
     * 描述 : 配置
     */
    @Autowired
    private RmsProperties rmsProperties;

    /**
     * 远程服务调用 (不走ribbon)
     *
     * @param host         地址
     * @param port         端口
     * @param serviceCode  服务代码
     * @param headers      headers
     * @param input        输入参数
     * @param uriParam     uri参数
     * @param responseType 返回类型
     * @param uriVariables rest参数
     * @param <I>          输入类型
     * @param <O>          输出类型
     * @return 服务结果
     */
    public <I, O> ResponseEntity<O> call(String host, int port, String serviceCode, HttpHeaders headers, I input, String uriParam, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) { //NOSONAR
        //客户端权限验证
        verification(serviceCode);
        //获取服务元数据
        ServiceMeta serviceMeta = rmsProperties.getService().get(serviceCode);
        //构建请求路径
        String path = (serviceMeta.getIsHttps() ? Constant.HTTPS : Constant.HTTP).concat(host).concat(":").concat(String.valueOf(port)).concat(serviceMeta.getUri());
        //获得请求方法
        String method = getRmsMethod(serviceCode);
        //拼装路径参数
        if (StringUtils.isNotBlank(uriParam)) {
            path += uriParam;
        }
        //构建请求头
        HttpHeaders httpHeaders = buildSystemTagHeaders(serviceCode);
        //添加请求头
        if (MapUtils.isNotEmpty(headers)) {
            httpHeaders.putAll(headers);
        }
        //构建请求消息体
        HttpEntity<I> requestEntity = new HttpEntity<>(input, httpHeaders);
        //请求并且返回
        log.debug("rms url : {} , method : {} ", path, method);
        return externalRestTemplate.exchange(path, HttpMethod.resolve(method), requestEntity, responseType, uriVariables != null ? uriVariables : new HashMap<String, String>());
    }

    /**
     * 远程服务调用 (不走ribbon)
     *
     * @param host         地址
     * @param port         端口
     * @param serviceCode  服务代码
     * @param input        输入参数
     * @param uriParam     uri参数
     * @param responseType 返回类型
     * @param uriVariables rest参数
     * @param <I>          输入类型
     * @param <O>          输出类型
     * @return 服务结果
     */
    public <I, O> ResponseEntity<O> call(String host, int port, String serviceCode, I input, String uriParam, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) {
        return this.call(host, port, serviceCode, null, input, uriParam, responseType, uriVariables);
    }

    /**
     * 描述 : 远程服务调用
     *
     * @param serviceCode  服务代码
     * @param headers      headers
     * @param input        输入参数
     * @param uriParam     uri参数
     * @param responseType 返回类型
     * @param uriVariables rest参数
     * @param <I>          输入类型
     * @param <O>          输出类型
     * @return 服务结果
     */
    public <I, O> ResponseEntity<O> call(String serviceCode, HttpHeaders headers, I input, String uriParam, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) {
        //客户端权限验证
        verification(serviceCode);
        //构建请求路径
        String path = getRmsUrl(serviceCode);
        //获得请求方法
        String method = getRmsMethod(serviceCode);
        //拼装路径参数
        if (StringUtils.isNotBlank(uriParam)) {
            path += uriParam;
        }
        //构建请求头
        HttpHeaders httpHeaders = buildSystemTagHeaders(serviceCode);
        //添加请求头
        if (MapUtils.isNotEmpty(headers)) {
            httpHeaders.putAll(headers);
        }
        //构建请求消息体
        HttpEntity<I> requestEntity = new HttpEntity<>(input, httpHeaders);
        //请求并且返回
        log.debug("rms url : {} , method : {} ", path, method);
        return restTemplate.exchange(path, HttpMethod.resolve(method), requestEntity, responseType, uriVariables != null ? uriVariables : new HashMap<String, String>());
    }

    /**
     * 描述 : 远程服务调用
     *
     * @param serviceCode  服务代码
     * @param input        输入参数
     * @param uriParam     uri参数
     * @param responseType 返回类型
     * @param uriVariables rest参数
     * @param <I>          输入类型
     * @param <O>          输出类型
     * @return 服务结果
     */
    public <I, O> ResponseEntity<O> call(String serviceCode, I input, String uriParam, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) {
        return this.call(serviceCode, null, input, uriParam, responseType, uriVariables);
    }

    /**
     * 描述 : 构建请求头
     *
     * @param serviceCode 服务代码
     * @return 请求头
     */
    private HttpHeaders buildSystemTagHeaders(String serviceCode) {
        String secret = rmsProperties.getApplication().get(springApplicationName).getSecret();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.HEADER_RMS_APPLICATION_NAME_CODE, springApplicationName);
        headers.add(Constant.HEADER_RMS_SIGN_CODE, Constant.sign(springApplicationName, secret));
        headers.add(Constant.HEADER_SERVICE_CODE_CODE, serviceCode);
        return headers;
    }

    /**
     * 描述 : 客户端验证
     *
     * @param serviceCode 服务代码
     */
    private void verification(String serviceCode) {
        ApplicationMeta applicationMeta = rmsProperties.getApplication().get(springApplicationName);
        if (!applicationMeta.getAll()) {
            if (applicationMeta.getDisabled()) {
                throw new PermissionException(springApplicationName + " is disabled");
            }
            if (applicationMeta.getPurview().indexOf(serviceCode) == -1) {
                throw new PermissionException("no access to this servoceCode : " + serviceCode);
            }
        }
    }

    /**
     * 描述 : 获得请求方法
     *
     * @param serviceCode 服务代码
     * @return 请求方法
     */
    private String getRmsMethod(String serviceCode) {
        return rmsProperties.getService().get(serviceCode).getMethod();
    }

    /**
     * 描述 : 构造url;
     *
     * @param serviceCode 服务代码
     * @return url
     */
    private String getRmsUrl(String serviceCode) {
        //获取服务元数据
        ServiceMeta serviceMeta = rmsProperties.getService().get(serviceCode);
        //构建请求路径
        StringBuilder url = new StringBuilder(serviceMeta.getIsHttps() ? Constant.HTTPS : Constant.HTTP);
        url.append(rmsProperties.getApplication().get(serviceMeta.getOwner()).getServiceId());
        url.append(serviceMeta.getUri());
        return url.toString();
    }

}
