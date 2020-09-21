/*   
 * Project: OSMP
 * FileName: LoginService.java
 * version: V1.0
 */
package com.osmp.web.panel.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * 
 * @author: 车龙泉
 * @date: 2015年4月17日 下午13：43
 */
public interface LoginService {

	public Map<String, Object> login(HttpServletRequest req, String userName, String password);

}
