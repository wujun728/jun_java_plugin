package org.typroject.tyboot.core.foundation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;


public class ApiClient {


    private final Logger logger = LogManager.getLogger(ApiClient.class);


    private RestTemplate restTemplate;


    public ApiClient(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }




    /**
     * 发送远程调用请求
     * @param headers
     * @param pathParams
     * @param requestParams
     * @param body
     * @return
     * @throws Exception
     */
    public Object exchange(ApiInfo apiInfo, Map<String,String> headers,Map<String,String> pathParams,Map<String,String> requestParams,Map<String,Object> body) throws Exception
    {
        Object returnObj = null;
        if(!ValidationUtil.isEmpty(apiInfo))
        {
            String url                              = this.paresUrl(apiInfo.getUrl(),pathParams,requestParams);
            RequestEntity requestEntity             = new RequestEntity(body,asymbleHeaders(headers), HttpMethod.resolve(apiInfo.getHttpMethod()), new URI(url));
            ResponseEntity<Object> responseEntity   = restTemplate.exchange(requestEntity,Object.class);
            returnObj                               = this.analysisResult(responseEntity);
        }
        return returnObj;
    }


    private Object analysisResult(ResponseEntity responseEntity) throws Exception
    {
        Object returnObj = responseEntity.getBody();
        if(responseEntity.getStatusCode().value() != 200)
        {
            Exception e = new Exception("远程调用异常.");
            logger.error("远程调用发生未知异常.",e);
            throw e;
        }
        return returnObj;
    }



    private String paresUrl(String defindedUrl ,Map<String,String> pathParams,Map<String,String> requestParams)
    {

        //处理地址参数
        if(!ValidationUtil.isEmpty(pathParams))
        {
            for(String paramName:pathParams.keySet())
            {
                defindedUrl = defindedUrl.replaceAll("\\{"+paramName+"}",pathParams.get(paramName));
            }

        }

        //处理查询条件参数

        if(!ValidationUtil.isEmpty(requestParams))
        {
            defindedUrl = defindedUrl+"?";
            for(String paramName:requestParams.keySet())
            {
                defindedUrl = defindedUrl +paramName+"=" +requestParams.get(paramName)+"&";
            }
            defindedUrl  = defindedUrl +"_rd="+String.valueOf((int)(Math.random()*10000000000L));
        }

        return defindedUrl;
    }


    private HttpHeaders asymbleHeaders(Map<String,String> header)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(!ValidationUtil.isEmpty(header))
            for(String key:header.keySet())
                httpHeaders.add(key,header.get(key));
        return httpHeaders;
    }








}
