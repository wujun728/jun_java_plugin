package org.ws.httphelper.model;

import org.apache.http.NameValuePair;
import org.ws.httphelper.annotation.WSRequest.MethodType;
import org.ws.httphelper.annotation.WSRequest.ResponseType;
import org.ws.httphelper.model.config.HandlerData;

import java.util.*;

/**
 * Created by gz on 15/11/29.
 */
public class WSRequestContext {
    /**
     * 名称
     */
    private String name;
    /**
     * 头列表
     */
    private Map<String, String> headerMap;
    /**
     * 参数
     */
    private List<ParameterDefine> parameterDefineList;
    /**
     * 验证结果列表
     */
    private List<ErrorMessage> errorMessageList;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求方法
     */
    private MethodType method;
    /**
     * 相应类型
     */
    private ResponseType responseType;
    /**
     * 描述
     */
    private String description;
    /**
     * 输入
     */
    private Map<String, Object> inputDataMap;
    /**
     * 请求参数
     */
    private List<NameValuePair> nameValuePairList;
    /**
     * cookie
     */
    private Map<String, String> cookieMap;
    /**
     * 编码
     */
    private String charset;
    /**
     * 多媒体参数
     */
    private Map<String, Object> multipartDataMap;
    /**
     * 返回值类型：用于自动解析
     */
    private Class<?> resultClass;

    public WSRequestContext() {
        headerMap = new HashMap<String, String>();
        parameterDefineList = new ArrayList<ParameterDefine>();
        errorMessageList = new ArrayList<ErrorMessage>();
        cookieMap = new HashMap<String, String>();
        multipartDataMap = new HashMap<String, Object>();
        inputDataMap = new HashMap<String, Object>();
    }

    public String getName() {
        return name;
    }

    public WSRequestContext setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public WSRequestContext addHeader(String name, String value) {
        this.headerMap.put(name, value);
        return this;
    }

    public List<ParameterDefine> getParameterDefineList() {
        return parameterDefineList;
    }

    public WSRequestContext addParameterDefine(ParameterDefine parameterDefine) {
        if (!containsParameterDefine(parameterDefine.getName())) {
            this.parameterDefineList.add(parameterDefine);
        }
        return this;
    }

    public boolean containsParameterDefine(String name) {
        for (ParameterDefine parameter : this.parameterDefineList) {
            if (parameter.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    public WSRequestContext setUrl(String url) {
        this.url = url;
        return this;
    }

    public MethodType getMethod() {
        return method;
    }

    public WSRequestContext setMethod(MethodType method) {
        this.method = method;
        return this;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }

    public WSRequestContext setResultClass(Class<?> resultClass) {
        this.resultClass = resultClass;
        return this;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public WSRequestContext setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WSRequestContext setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, Object> getInputDataMap() {
        return inputDataMap;
    }

    public WSRequestContext addInputData(String key, Object value) {
        getInputDataMap().put(key, value);
        return this;
    }

    public Map<String, Object> getMultipartDataMap() {
        return multipartDataMap;
    }

    public WSRequestContext setMultipartDataMap(Map<String, Object> multipartDataMap) {
        this.multipartDataMap = multipartDataMap;
        return this;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public WSRequestContext addValidationResult(ErrorMessage errorMessage) {
        this.errorMessageList.add(errorMessage);
        return this;
    }

    public List<NameValuePair> getNameValuePairList() {
        return nameValuePairList;
    }

    public WSRequestContext setNameValuePairList(List<NameValuePair> nameValuePairList) {
        this.nameValuePairList = nameValuePairList;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public WSRequestContext addCookie(String name, String value) {
        this.cookieMap.put(name, value);
        return this;
    }

    /**
     * 不清楚cookie
     */
    public void clear() {
        if (this.inputDataMap != null) {
            this.inputDataMap.clear();
        }
        if (this.headerMap != null) {
            this.headerMap.clear();
        }
        if (this.multipartDataMap != null) {
            this.multipartDataMap.clear();
        }
        if (this.nameValuePairList != null) {
            this.nameValuePairList.clear();
        }
        if (this.errorMessageList != null) {
            this.errorMessageList.clear();
        }
        if (this.parameterDefineList != null) {
            this.parameterDefineList.clear();
        }
    }

    public void clearCookie() {
        if (this.cookieMap != null) {
            this.cookieMap.clear();
        }
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("name=").append(name).append("\n")
                .append("description=").append(description).append("\n")
                .append("url=").append(url).append("\n")
                .append("method=").append(method.name()).append("\n")
                .append("responseType=").append(responseType.name()).append("\n");
        if (headerMap != null) {
            str.append("headers:\n");
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(headerMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (cookieMap != null) {
            str.append("cookies:\n");
            Set<String> keySet = cookieMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(cookieMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (nameValuePairList != null) {
            str.append("parameters:\n");
            for (NameValuePair nvp : nameValuePairList) {
                str.append(nvp.getName()).append("=").append(nvp.getValue()).append(";");
            }
            str.append("\n");
        }
        if (multipartDataMap != null) {
            str.append("multipartData:\n");
            Set<String> keySet = multipartDataMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(multipartDataMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (parameterDefineList != null) {
            str.append("parameterDefine:\n");
            for (ParameterDefine parameter : parameterDefineList) {
                str.append(parameter.getName()).append(";");
            }
            str.append("\n");
        }

        if (inputDataMap != null) {
            str.append("inputData:\n");
            Set<String> keySet = inputDataMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(inputDataMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
