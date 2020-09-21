/*   
 * Project: OSMP
 * FileName: DataServiceProxy.java
 * version: V1.0
 */
package com.osmp.http.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.osmp.http.tool.DefaultServiceInvocation;
import com.osmp.intf.define.model.InvocationDefine;
import com.osmp.intf.define.model.Parameter;
import com.osmp.intf.define.model.ServiceContext;
import com.osmp.utils.base.JSONUtils;

/**
 * 服务调用代理类
 * @author heyu
 *
 */
public class DataServiceProxy {
    private Logger logger = LoggerFactory.getLogger(DataServiceProxy.class);
    
    private InvocationDefine invocationDefine;
    private ServiceContext context;
    
    private boolean isError = false;
    
    public boolean isError(){
        return isError;
    }
    
    public InvocationDefine getInvocationDefine() {
        return invocationDefine;
    }

    public void setInvocationDefine(InvocationDefine invocationDefine) {
        this.invocationDefine = invocationDefine;
    }

    public ServiceContext getContext() {
        return context;
    }

    public void setContext(ServiceContext context) {
        this.context = context;
    }

    public DataServiceProxy() {
        super();
    }

    public DataServiceProxy(InvocationDefine invocationDefine, ServiceContext context) {
        super();
        this.invocationDefine = invocationDefine;
        this.context = context;
    }
    
    private void initParameter(){
        Map<String, String> queryMap = null;
        queryMap = JSONUtils.jsonString2Map(context.getQueryString());
        if(queryMap == null) {
            queryMap = new HashMap<String, String>();
        }
        context.setParametor(new Parameter(context.getClientInfo(), context.getQueryString(),queryMap));
    }
    
    
    public Object execute(){
        Assert.notNull(invocationDefine,"invocationDefine not set...");
        Assert.notNull(context, "context not set..");
        initParameter();
        DefaultServiceInvocation invocation = new DefaultServiceInvocation(invocationDefine.getInterceptors(), invocationDefine.getDataService(), context);
        Object data = invocation.process();
        isError = invocation.isError();
        return data;
    }
    
   
}


