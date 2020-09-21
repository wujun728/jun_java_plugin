/*   
 * Project: OSMP
 * FileName: BaseTaskService.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

import com.osmp.intf.define.model.Parameter;

/**
 * Description:基础任务服务(任务 异步)
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:32:40上午10:51:30
 */
public interface BaseTaskService {
	/**
	 * 任务，异步执行器，必须实现
	 * @param parameter
	 */
    void execute(Parameter parameter);
}
