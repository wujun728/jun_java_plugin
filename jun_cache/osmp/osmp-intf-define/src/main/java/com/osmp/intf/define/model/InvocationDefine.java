/*   
 * Project: OSMP
 * FileName: InvocationDefine.java
 * version: V1.0
 */
package com.osmp.intf.define.model;

import java.util.List;

import com.osmp.intf.define.interceptor.ServiceInterceptor;
import com.osmp.intf.define.service.BaseDataService;

/**
 * Description:执行器定义，包含也目标服务，绑定的拦截器
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:27:54上午10:51:30
 */
public class InvocationDefine {
    private BaseDataService dataService;
    private List<ServiceInterceptor> interceptors;
    public BaseDataService getDataService() {
        return dataService;
    }
    public void setDataService(BaseDataService dataService) {
        this.dataService = dataService;
    }
    public List<ServiceInterceptor> getInterceptors() {
        return interceptors;
    }
    public void setInterceptors(List<ServiceInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
    
}
