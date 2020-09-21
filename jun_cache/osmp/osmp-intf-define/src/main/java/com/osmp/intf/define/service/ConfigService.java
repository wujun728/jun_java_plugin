/*   
 * Project: OSMP
 * FileName: ConfigService.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

import java.util.Map;

/**
 * Description:配置服务接口 用来接收管理平台下发的配置
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:34:21上午10:51:30
 */
public interface ConfigService {
	static final String DATASERVICE_CONFIG = "dataService";
	static final String INTERCEPTOR_CONFIG = "interceptor";
	static final String LOG_SWITCH = "logSwitch";
	static final String MONITOR_SWITCH = "monitorSwitch";
	static final String MMS_SWITCH = "mmsSwitch";
	static final String MAIL_SWITCH = "mailSwitch";

	/**
	 * 更新
	 * @param target 更新目标
	 */
	void update(String target);
	/**
     * 携值更新
     * @param target 更新目标
     * @param value 更新值
     */
	void update(String target,String value);

	/**
	 * 获取数据
	 * @param target 获取目标
	 * @param args 参数信息
	 * @return
	 */
	Object getData(String target, Map<String, Object> args);
}
