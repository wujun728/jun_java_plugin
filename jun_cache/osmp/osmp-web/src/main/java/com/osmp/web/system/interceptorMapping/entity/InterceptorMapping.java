package com.osmp.web.system.interceptorMapping.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class InterceptorMapping {
    @Id
    private int id;
    @Column
    private String interceptorBundle;
    @Column
    private String interceptorVersion;
    @Column
    private String interceptorName;
    @Column
    private String serviceBundle;
    @Column
    private String serviceVersion;
    @Column
    private String serviceName;
    private String mark;
    @Column
    private int level;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public String getMark() {
        return mark;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }  
    
}
