package org.springrain.selenium.service;

import org.springrain.selenium.entity.Htmlfield;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-10-24 15:34:38
 * @see org.springrain.selenium.service.Htmlfield
 */
public interface IHtmlfieldService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Htmlfield findHtmlfieldById(Object id) throws Exception;
	
	
	
}
