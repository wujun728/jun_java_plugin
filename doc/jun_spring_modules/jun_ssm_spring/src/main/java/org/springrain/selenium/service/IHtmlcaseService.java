package org.springrain.selenium.service;

import org.springrain.selenium.entity.Htmlcase;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-10-25 12:24:30
 * @see org.springrain.selenium.service.Htmlcase
 */
public interface IHtmlcaseService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Htmlcase findHtmlcaseById(Object id) throws Exception;
	
	
	
}
