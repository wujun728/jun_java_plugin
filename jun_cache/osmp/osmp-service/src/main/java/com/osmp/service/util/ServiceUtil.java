/*   
 * Project: OSMP
 * FileName: ServiceUtil.java
 * version: V1.0
 */
package com.osmp.service.util;

public class ServiceUtil {
    
    /**
     * 生成service名称
     * @param bundle 组件名称
     * @param version 组件版本号
     * @param name 服务名称
     * @return
     */
    public final static String generateServiceName(String bundle,String version,String name){
        return bundle + "(" + version + ")" + "-" + name;
    }
}
