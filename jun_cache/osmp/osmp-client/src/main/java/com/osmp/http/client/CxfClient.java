/*   
 * Project: OSMP
 * FileName: DefaultCxfClient.java
 * version: V1.0
 */
package com.osmp.http.client;

import java.util.List;
/**
 * cxf客戶端接口
 * @author heyu
 *
 */
public interface CxfClient {
    /**
     * get方法获取对象
     * @param path 接口名称
     * @param parameter 传递的参数
     * @param clz 返回的对象
     * @return
     * @throws Exception
     */
    <T> T getForObject(String path,Object parameter,Class<T> clz) throws Exception;
    
    /**
     * get方法获取对象集合
     * @param path 接口名称
     * @param parameter 传递的参数
     * @param clz 返回的对象
     * @return
     * @throws Exception
     */
    <T> List<T> getForList(String path,Object parameter,Class<T> clz) throws Exception;
    
    /**
     * post方法获取对象集合
     * @param path 接口名称
     * @param parameter 传递的参数
     * @param clz 返回的对象
     * @return
     * @throws Exception
     */
    <T> T postForObject(String path,Object parameter,Class<T> clz) throws Exception;
    
    /**
     * post方法获取对象集合
     * @param path 接口名称
     * @param parameter 传递的参数
     * @param clz 返回的对象
     * @return
     * @throws Exception
     */
    <T> List<T> postForList(String path,Object parameter,Class<T> clz) throws Exception;
}
