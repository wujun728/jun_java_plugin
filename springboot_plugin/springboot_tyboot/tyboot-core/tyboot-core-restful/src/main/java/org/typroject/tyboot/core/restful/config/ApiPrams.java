package org.typroject.tyboot.core.restful.config;

import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.*;

/**
 * Created by yaohelang on 2018/6/25.
 */
public  class ApiPrams {


    private SortedMap<String,String> pathParmas;
    private SortedMap<String,String> requestParmas;
    private Map<String,Object> requestBody;
    private TyApiInfo  tyApiInfo;


    private ApiPrams (TyApiInfo tyApiInfo)
    {
        this.tyApiInfo = tyApiInfo;
        this.pathParmas = new TreeMap<>();
        this.requestParmas = new TreeMap<>();
        this.requestBody  = new HashMap<>();
    }


    public static  ApiPrams build(TyApiInfo tyApiInfo)
    {
        return new ApiPrams(tyApiInfo);
    }

    /**
     * 填写地址参数，按参数名称的字典顺序填写。
     * @param pathparams
     * @return
     */
    public final ApiPrams putPathparams(String...pathparams)
    {

        if(!ValidationUtil.isEmpty(pathparams) && pathparams.length == this.tyApiInfo.getUriParamsName().size())
        {
            Iterator<String> it = this.tyApiInfo.getUriParamsName().iterator();
            for(int i=0 ;i<pathparams.length && it.hasNext();i++)
            {
                this.pathParmas.put(it.next(),pathparams[i]);
            }
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
        Iterator<String> it = this.tyApiInfo.getRequestParamsName().iterator();
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
