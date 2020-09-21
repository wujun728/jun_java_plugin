/*   
 * Project: OSMP
 * FileName: ServiceInvocation.java
 * version: V1.0
 */
package com.osmp.intf.define.interceptor;

import com.osmp.intf.define.model.ServiceContext;

/**
 * Description:服务执行接口
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:27:15上午10:51:30
 */
public interface ServiceInvocation {
    ServiceContext getContext();
    Object process();
}
