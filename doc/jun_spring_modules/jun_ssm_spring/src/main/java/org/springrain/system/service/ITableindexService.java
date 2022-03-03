package org.springrain.system.service;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 14:16:38
 * @see org.springrain.demo.service.Tableindex
 */
public interface ITableindexService extends IBaseSpringrainService {
	
	/**
	 * 根据类名,获取当前对象一个新的Id,为空则返回NULL
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("rawtypes")
	String	updateNewId(Class clazz) throws Exception;
	
	
	
	
	
	
}
