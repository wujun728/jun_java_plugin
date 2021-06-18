package org.typroject.tyboot.core.restful.config;

import org.springframework.http.HttpMethod;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.component.cache.Redis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

public class TyApiInfo implements Serializable{


    public  static final String TYAPIINFO = "TYAPIINFO";

    public TyApiInfo()
    {
        this.header = new HashMap<>();
        this.header.put("Content-Type","application/json");
        this.header.put("token","");
    }


    public static String getCacheKeyForTyApiInfoOfHash()
    {
       return  Redis.genKey(CacheType.INERASABLE.name(),TYAPIINFO);
    }


    private String apiCode;//模块.资源.方法名。 全局唯一
    private String url;
    private HttpMethod method;
    private Map<String,String> header;
    private SortedSet<String> requestParamsName;
    private SortedSet<String> uriParamsName;
    private Map<String,Object> body;


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

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod HttpMethod) {
        this.method = method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void addHeader(String name,String value) {
        this.header = header;
    }

    public SortedSet<String> getRequestParamsName() {
        return requestParamsName;
    }

    public void setRequestParamsName(SortedSet<String> requestParamsName) {
        this.requestParamsName = requestParamsName;
    }

    public SortedSet<String> getUriParamsName() {
        return uriParamsName;
    }

    public void setUriParamsName(SortedSet<String> uriParamsName) {
        this.uriParamsName = uriParamsName;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;

    }
}
