/*
 * @(#)ResponseData.java
 * Copyright (C) 2018 Neusoft Corporation All rights reserved.
 *
 * VERSION        DATE       BY              CHANGE/COMMENT
 * ----------------------------------------------------------------------------
 * @version 1.00  2018-03-21 liubsh          初版
 *
 */

package com.snakerflow.demo.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * TODO 类型描述。
 *
 * @since inkstone-project-web v1.0
 * @version 1.00
 * @author liubsh
 */
public class ResponseData {
    
    public static final String ERRORS_KEY = "errors";  
    
    private final String message;  
    private final int code;  
    private final Map<String, Object> body = new HashMap<>();  
  
    public String getMessage() {  
        return message;  
    }  
  
    public int getCode() {  
        return code;  
    }  
  
    public Map<String, Object> getBody() {  
        return body;  
    }  
      
    public ResponseData putDataValue(String key, Object value) {  
        body.put(key, value);  
        return this;  
    }  
      
    private ResponseData(int code, String message) {  
        this.code = code;  
        this.message = message;  
    }  
    
    /**
     * TODO 方法说明。
     * [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
     * @return
     */
    public static ResponseData ok() {  
        return new ResponseData(200, "OK");  
    }  
    
    /**
     * TODO 方法说明。
     * [POST/PUT/PATCH]：用户新建或修改数据成功。
     * @return
     */
    public static ResponseData createdOrUpdate() {  
        return new ResponseData(201, "CREATED");  
    }  
    
    /**
     * TODO 方法说明。
     * [*]：表示一个请求已经进入后台排队（异步任务）
     * @return
     */
    public static ResponseData acceptedRequest() {  
        return new ResponseData(202, "Accepted");  
    }  
      
    /**
     * TODO 方法说明。
     * [DELETE]：用户删除数据成功。
     * @return
     */
    public static ResponseData deleteOk() {  
        return new ResponseData(204, "NO CONTENT");  
    }  
    
    /**
     * TODO 方法说明。
     * 未授权访问,表示用户没有权限（令牌、用户名、密码错误）
     * @return
     */
    public static ResponseData unauthorized() {  
        return new ResponseData(401, "未授权访问!");  
    } 
    
    /**
     * TODO 方法说明。
     * 越权访问,没有修改资源的访问权限
     * @return
     */
    public static ResponseData outOfBounds() {  
        return new ResponseData(403, "您没有改资源的访问权限!");  
    }  
      
    
    /**
     * TODO 方法说明。
     * 没有找到对象
     * @return
     */
    public static ResponseData notFound() {  
        return new ResponseData(404, "not found");  
    }
    
    /**
     * TODO 方法说明。
     * 方法错误
     * @return
     */
    public static ResponseData serverInternalError() {  
        return new ResponseData(500, "Server Internal Error");  
    }

    public static void setResponse(HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("access-control-allow-methods", "POST, GET,HEAD, OPTIONS,PATCH, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("access-control-allow-credentials", "true");
    }

      
    /*
     *   调用实例
     *   @GetMapping  
     *   @ResponseBody  
     *   public Map<String, Object> index() {  
     *        
     *      Map<String, Object> response = new HashMap<>();  
     *      response.put("code", 200);  
     *      response.put("message", "Ok");  
     *      response.put("body", new HashMap<String, String>());  
     *      return response;  
     *  }  
     *   
     *  @GetMapping("/test_response_data")  
     *  @ResponseBody  
     *  public ResponseData testResponseData() { 
     *   
     *      return ResponseData.ok().putDataValue("token", "XXXXXXXXXXXXXXXXXXXXXXX");  
     * 
     *  }  
     * 
     */

}
