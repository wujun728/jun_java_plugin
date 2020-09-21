/*   
 * Project: OSMP
 * FileName: BaseDataService.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

import com.osmp.intf.define.model.Parameter;

/**
 * Description:基础数据服务接口
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:31:05上午10:51:30
 */
public interface BaseDataService {

	/**
	 * 服务执行接口,必须实现此接口
	 * @param parameter
	 * @return
	 */
    Object execute(Parameter parameter);
}
