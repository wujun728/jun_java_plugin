/*   
 * Project: OSMP
 * FileName: ServiceContext.java
 * version: V1.0
 */
package com.osmp.intf.define.model;

import java.util.Map;

/**
 * Description:服务上下文
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:29:41上午10:51:30
 */
public class ServiceContext {
    private Map<String, String> clientInfo;
    private Parameter parameter;
    private String serviceName;
    private String queryString;
    
    public Map<String, String> getClientInfo() {
        return clientInfo;
    }
    public Parameter getParameter() {
        return parameter;
    }
    public void setParametor(Parameter parameter) {
        this.parameter = parameter;
    }
    public String getServiceName() {
        return serviceName;
    }
    public String getQueryString() {
        return queryString;
    }
    public ServiceContext(Map<String, String> clientInfo, String serviceName, String queryString) {
        this.clientInfo = clientInfo;
        this.serviceName = serviceName;
        this.queryString = queryString;
    }
    
}
