/*   
 * Project: OSMP
 * FileName: InterceptorMappingInfo.java
 * version: V1.0
 */
package com.osmp.service.bean;

import com.osmp.jdbc.define.Column;
import com.osmp.service.util.ServiceUtil;

public class InterceptorMappingInfo {
    @Column(mapField="interceptorBundle",name="interceptorBundle")
    private String interceptorBundle;
    @Column(mapField="interceptorVersion",name="interceptorVersion")
    private String interceptorVersion;
    @Column(mapField="interceptorName",name="interceptorName")
    private String interceptorName;
    @Column(mapField="serviceBundle",name="serviceBundle")
    private String serviceBundle;
    @Column(mapField="serviceVersion",name="serviceVersion")
    private String serviceVersion;
    @Column(mapField="serviceName",name="serviceName")
    private String serviceName;
    public String getInterceptorBundle() {
        return interceptorBundle;
    }
    public void setInterceptorBundle(String interceptorBundle) {
        this.interceptorBundle = interceptorBundle;
    }
    public String getInterceptorVersion() {
        return interceptorVersion;
    }
    public void setInterceptorVersion(String interceptorVersion) {
        this.interceptorVersion = interceptorVersion;
    }
    public String getInterceptorName() {
        return interceptorName;
    }
    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }
    public String getServiceBundle() {
        return serviceBundle;
    }
    public void setServiceBundle(String serviceBundle) {
        this.serviceBundle = serviceBundle;
    }
    public String getServiceVersion() {
        return serviceVersion;
    }
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String generateServiceName(){
        return ServiceUtil.generateServiceName(serviceBundle, serviceVersion, serviceName);
    }
    
    public String generateInterceptorName(){
        return ServiceUtil.generateServiceName(interceptorBundle, interceptorVersion, interceptorName);
    }
}
