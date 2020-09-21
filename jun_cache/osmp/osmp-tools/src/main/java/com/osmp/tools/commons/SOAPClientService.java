/*   
 * Project: OSMP
 * FileName: SOAPClientService.java
 * version: V1.0
 */
package com.osmp.tools.commons;

import javax.xml.soap.SOAPException;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:04:44
 */

public interface SOAPClientService {
	String SOAP_TIMEOUT = "600";

	<T extends Object> T sendRequest(String address, Object request, Class<T> response) throws SOAPException;

}
