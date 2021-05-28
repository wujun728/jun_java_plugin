package org.typroject.tyboot.component.opendata.im.easemob;

import org.springframework.http.HttpMethod;

public enum EasemobApiInfoBase implements EasemobApiInfo {


    //获取token
    GET_TOKEN(HttpMethod.POST,"/{org_name}/{app_name}/token",new String []{""},new String []{"org_name","app_name"}),



    //用户体系集成--注册 IM 用户
    REGISTER(HttpMethod.POST,"/{org_name}/{app_name}/users",new String []{""},new String []{"org_name","app_name"})
    ;






    private HttpMethod method;

    private String url;
    private String [] requestParams;
    private String [] pathParams;

    EasemobApiInfoBase(HttpMethod method, String url, String [] requestParams, String [] pathParams)
    {
        this.method = method;
        this.pathParams = pathParams;
        this.requestParams= requestParams;
        this.url = url;
    }


    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String[] getRequestParams() {
        return requestParams;
    }

    @Override
    public String[] getPathParams() {
        return pathParams;
    }
}
