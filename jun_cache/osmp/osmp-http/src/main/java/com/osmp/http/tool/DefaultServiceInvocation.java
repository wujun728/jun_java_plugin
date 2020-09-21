/*   
 * Project: OSMP
 * FileName: DefaultServiceInvocation.java
 * version: V1.0
 */
package com.osmp.http.tool;

import java.util.List;

import com.osmp.intf.define.interceptor.ServiceInterceptor;
import com.osmp.intf.define.interceptor.ServiceInvocation;
import com.osmp.intf.define.model.ServiceContext;
import com.osmp.intf.define.service.BaseDataService;

/**
 * 服务拦截器调用
 * @author heyu
 *
 */
public class DefaultServiceInvocation implements ServiceInvocation {
    private int currentInterceptorIndex = 0;
    private List<ServiceInterceptor> interceptorChain;
    private BaseDataService dataService;
    private ServiceContext context;
    private boolean isError = true;
    
    @Override
    public Object process() {
        if(interceptorChain == null || interceptorChain.isEmpty()){
            isError = false;
            return dataService.execute(context.getParameter());
        }
        if(currentInterceptorIndex == interceptorChain.size()){
            isError =false;
            return dataService.execute(context.getParameter());
        }
        ++currentInterceptorIndex;
        return interceptorChain.get(currentInterceptorIndex-1).invock(this);
    }

    public DefaultServiceInvocation(List<ServiceInterceptor> interceptorChain,
            BaseDataService dataService, ServiceContext context) {
        super();
        this.interceptorChain = interceptorChain;
        this.dataService = dataService;
        this.context = context;
    }

    @Override
    public ServiceContext getContext() {
        return context;
    }

    public boolean isError() {
        return isError;
    }
    

}
