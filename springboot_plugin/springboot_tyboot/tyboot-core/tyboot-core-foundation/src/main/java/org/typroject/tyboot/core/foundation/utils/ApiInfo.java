package org.typroject.tyboot.core.foundation.utils;


import java.io.Serializable;
import java.util.SortedMap;
import java.util.SortedSet;


/***
 * 通用接口模型
 */
public class ApiInfo implements Serializable{


    private String apiDesc;//接口名称或描述
    private String apiCode;//接口唯一标识
    private String url;     //全地址
    private String httpMethod;//http请求方式
    private SortedSet<String> requestParamsNames;//查询参数
    private SortedSet<String> pathVariableNames;//地址参数
    private SortedMap<String,Object> body;//body信息





    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }


    public SortedSet<String> getRequestParamsNames() {
        return requestParamsNames;
    }

    public void setRequestParamsNames(SortedSet<String> requestParamsNames) {
        this.requestParamsNames = requestParamsNames;
    }

    public SortedSet<String> getPathVariableNames() {
        return pathVariableNames;
    }

    public void setPathVariableNames(SortedSet<String> pathVariableNames) {
        this.pathVariableNames = pathVariableNames;
    }

    public SortedMap<String, Object> getBody() {
        return body;
    }

    public void setBody(SortedMap<String, Object> body) {
        this.body = body;
    }



}
