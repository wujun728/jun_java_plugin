/*   
 * Project: OSMP
 * FileName: ServiceInterceptor.java
 * version: V1.0
 */
package com.osmp.intf.define.interceptor;

/**
 * Description:拦截器
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:26:32上午10:51:30
 */
public interface ServiceInterceptor {
    Object invock(ServiceInvocation invocation);
}
