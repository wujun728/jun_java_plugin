package com.alibaba.boot.dubbo.generic;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GenericServiceConfig implements Serializable {
    private static final long serialVersionUID = 1064223171940612201L;

    //兼容 jsonrpc 如果携带次参数 将以jsonrpc 格式返回
    private String jsonrpc = "2.0";

    //兼容 jsonrpc
    private String id;

    //方法
    @NotNull
    private String method;

    //组名
    private String group;

    //版本
    private String version;

    //参数
    private JsonNode params;

    //参数类型
    private String[] paramsType;


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        if (StringUtils.isBlank(group)) {
            return;
        }
        this.group = group;
    }

    public String getMethod() {
        return method;
    }

    public GenericServiceConfig setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public GenericServiceConfig setVersion(String version) {
        if (StringUtils.isBlank(version)) {
            return this;
        }
        this.version = version;
        return this;
    }



    public GenericServiceConfig() {
    }

    @Override
    public String toString() {
        return "GenericServiceConfig{" +
                ", method='" + method + '\'' +
                ", group='" + group + '\'' +
                ", version='" + version + '\'' +
                ", params=" + params +
                '}';
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public GenericServiceConfig setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
        return this;
    }

    public String getId() {
        return id;
    }

    public GenericServiceConfig setId(String id) {
        this.id = id;
        return this;
    }

    public GenericServiceConfig setParams(JsonNode params) {
        this.params = params;
        return this;
    }

    public JsonNode getParams() {
        return params;
    }

    public String[] getParamsType() {
        return paramsType;
    }

    public GenericServiceConfig setParamsType(String[] paramsType) {
        this.paramsType = paramsType;
        return this;
    }
}