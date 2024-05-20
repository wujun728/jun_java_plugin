package com.jun.plugin.common.base.interfaces;

import java.util.Map;

public interface IExecutor<T, P extends Map> {
	
	/**
	 * 执行接口
	 * 
	 * @return
	 * @throws Exception 
	 */
	public T execute(P params) throws Exception;

	/**
	 * 回滚接口，业务补偿
	 * 
	 * @return
	 */
//	public T rollback(P params);

}
