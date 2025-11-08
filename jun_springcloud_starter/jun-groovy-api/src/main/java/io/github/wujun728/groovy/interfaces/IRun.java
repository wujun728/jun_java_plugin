package io.github.wujun728.groovy.interfaces;

import java.util.Map;

public interface IRun<T, P extends Map> {
	
	/**
	 * 执行接口
	 * 
	 * @return
	 * @throws Exception 
	 */
	public T run(P params) throws Exception;

	/**
	 * 回滚接口，业务补偿
	 * 
	 * @return
	 */
//	public T rollback(P params);

}
