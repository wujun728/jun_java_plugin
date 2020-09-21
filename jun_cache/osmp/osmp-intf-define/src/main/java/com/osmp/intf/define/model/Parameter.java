/*   
 * Project: OSMP
 * FileName: Parameter.java
 * version: V1.0
 */
package com.osmp.intf.define.model;

import java.util.Map;

/**
 * Description:参数定义
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:29:14上午10:51:30
 */
public class Parameter {
    private Map<String, String> clientInfo;
    private String queryString;
    private Map<String, String> queryMap;
    
    public Map<String, String> getClientInfo() {
        return clientInfo;
    }
    public String getQueryString() {
        return queryString;
    }
    public Map<String, String> getQueryMap() {
        return queryMap;
    }
    public String getClientProperty(String property){
        if(clientInfo != null){
            return clientInfo.get(property);
        }
        return null;
    }
    public Parameter(Map<String, String> clientInfo, String queryString, Map<String, String> queryMap) {
        super();
        this.clientInfo = clientInfo;
        this.queryString = queryString;
        this.queryMap = queryMap;
    }
    
}
