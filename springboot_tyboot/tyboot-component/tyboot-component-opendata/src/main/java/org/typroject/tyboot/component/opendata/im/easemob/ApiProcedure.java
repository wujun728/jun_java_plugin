package org.typroject.tyboot.component.opendata.im.easemob;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiProcedure {



    //@Value("${easemob.appkey}")
    private String appkey;

    //@Value("${easemob.clientid}")
    private String clientid;

    //@Value("${easemob.clientsecret}")
    private String clientsecret;

    //@Value("${easemob.org_name}")
    private String org_name;

    //@Value("${easemob.app_name}")
    private String app_name;



    private RestTemplate restTemplate;


    private static String BASE_URL = "http://a1.easemob.com";

    private static String token ;


    public ApiProcedure(RestTemplate restTemplate,String appkey,String clientid,String clientsecret,String org_name,String app_name) {
        this.restTemplate   = restTemplate;
        this.appkey         = appkey;
        this.clientid       = clientid;
        this.clientsecret   = clientsecret;
        this.org_name       = org_name;
        this.app_name       = app_name;
    }

    public   Map call(EasemobApiInfoBase apiInfo, Map<String,Object> body , Map<String,String> otherPathParams)throws Exception
    {
        HttpHeaders header = new HttpHeaders();
        List headerValue = new ArrayList<>();
        headerValue.add("application/json;charset=UTF-8");


        List tokenValue = new ArrayList<>();

        tokenValue.add("Bearer "+getToken());
        header.put("Authorization",tokenValue);
        Map<String,String> pathParams = new HashMap();
        pathParams.put("org_name",org_name);
        pathParams.put("app_name",app_name);
        pathParams.putAll(otherPathParams);

       return this.sendRequest(apiInfo.getUrl(),apiInfo.getMethod(),body,pathParams,header);
    }


    private Map sendRequest(String url, HttpMethod method,Map<String, Object> body,Map<String,String> pathParams,HttpHeaders headers) throws Exception
    {
        String allUrl = BASE_URL+url;
        for(String key : pathParams.keySet())
        {
            allUrl =  allUrl.replaceAll("\\{"+key+"}",pathParams.get(key));
        }
        RequestEntity entity = new RequestEntity(body,headers,method,new URI(allUrl));

        ResponseEntity result = restTemplate.exchange(BASE_URL+url, method, entity, Map.class, pathParams);
        Map map = (Map)result.getBody();
        return map;
    }





    private String  getToken() throws Exception
    {
        if(ValidationUtil.isEmpty(token))
        {
            HttpHeaders header = new HttpHeaders();
            List headerValue = new ArrayList<>();
            headerValue.add("application/json;charset=UTF-8");
            Map<String,String> pathParams = new HashMap();
            pathParams.put("org_name",org_name);
            pathParams.put("app_name",app_name);


            Map<String,Object> body = new HashMap<>();
            body.put("grant_type","client_credentials");
            body.put("client_id",clientid);
            body.put("client_secret",clientsecret);

            Map tokenResult  = this.sendRequest(EasemobApiInfoBase.GET_TOKEN.getUrl(), EasemobApiInfoBase.GET_TOKEN.getMethod(),body,pathParams,header);
            if(!ValidationUtil.isEmpty(tokenResult) && !ValidationUtil.isEmpty(tokenResult.get("access_token")))
            {
                token = tokenResult.get("access_token").toString();
            }
        }

        return token;
    }







}
