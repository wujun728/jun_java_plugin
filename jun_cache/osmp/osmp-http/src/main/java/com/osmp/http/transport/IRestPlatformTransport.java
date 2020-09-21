/*   
 * Project: OSMP
 * FileName: IRestPlatformTransport.java
 * version: V1.0
 */
package com.osmp.http.transport;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Description:统一服务接口 REST
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:14:42上午10:51:30
 */
public interface IRestPlatformTransport {
    
    /**
     * 获取数据
     * @param source 来源 json格式{from:xx}
     * @param interfaceName 接口名称，对应服务
     * @param parameter 接口参数，对应服务参数
     * @return
     */
    public Response data(String source,String interfaceName,String parameter,HttpServletRequest request);
    /**
     * 获取数据
     * @param source 来源 json格式{from:xx}
     * @param interfaceName 接口名称，对应服务
     * @param parameter 接口参数，对应服务参数
     * @return
     */
    public Response dataPost(String source,String interfaceName,String parameter,HttpServletRequest request);    
    /**
     * 提交任务
     * @param source 来源 json格式{from:xx}
     * @param interfaceName 接口名称，对应服务
     * @param parameter 接口参数，对应服务参数
     * @return
     */
    public Response task(String source,String interfaceName,String parameter);
}
