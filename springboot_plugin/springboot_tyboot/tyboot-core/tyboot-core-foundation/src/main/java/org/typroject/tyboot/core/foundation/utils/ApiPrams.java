package org.typroject.tyboot.core.foundation.utils;

import java.util.*;

/**
 * Created by yaohelang on 2018/6/25.
 */
public  class ApiPrams {


    private SortedMap<String,String> pathParmas;
    private SortedMap<String,String> requestParmas;
    private Map<String,Object> requestBody;
    private ApiInfo  apiInfo;


    private ApiPrams(ApiInfo apiInfo)
    {
        this.apiInfo = apiInfo;
        this.pathParmas = new TreeMap<>();
        this.requestParmas = new TreeMap<>();
        this.requestBody  = new HashMap<>();
    }


    public static  ApiPrams build(ApiInfo apiInfo)
    {
        return new ApiPrams(apiInfo);
    }


    /**
     * 填写地址参数
     * @param paramName
     * @param paramValue
     * @return
     */
    public final ApiPrams putPathparams(String paramName,String paramValue)
    {

        if(!ValidationUtil.isEmpty(paramName) && !ValidationUtil.isEmpty(paramValue) && this.apiInfo.getPathVariableNames().contains(paramName))
        {
           this.pathParmas.put(paramName,paramValue);
        }
        return this;
    }


    /**
     * 填写查询参数，按参数名称的字典顺序填写。
     * @param requestParams
     * @return
     */
    public final ApiPrams putRequestParams(String...requestParams)
    {
        Iterator<String> it = this.apiInfo.getRequestParamsNames().iterator();
        for(int i=0 ;i<requestParams.length && it.hasNext();i++)
        {
            this.requestParmas.put(it.next(),requestParams[i]);
        }
        return this;
    }


    /**
     * 填写body参数
     * @param
     * @return
     */
    public final ApiPrams putRequestBody(String paramName,Object value)
    {
        this.requestBody.put(paramName,value);
        return this;
    }


    public SortedMap<String, String> getPathParmas() {
        return pathParmas;
    }

    public SortedMap<String, String> getRequestParmas() {
        return requestParmas;
    }

    public Map<String, Object> getRequestBody() {
        return requestBody;
    }
}
