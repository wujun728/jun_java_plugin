/*   
 * Project: OSMP
 * FileName: WebServiceFactory.java
 * version: V1.0
 */
package com.osmp.tools.ws;

/**
 * Description:webService客户端代接口
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:19:04
 */

public interface WebServiceFactory {
	<T extends Object> T getClientProxy(String address, Class<T> serviceClass);
}
