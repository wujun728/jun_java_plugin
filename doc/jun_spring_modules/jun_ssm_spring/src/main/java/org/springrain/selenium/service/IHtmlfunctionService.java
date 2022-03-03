package org.springrain.selenium.service;

import org.springrain.selenium.entity.Htmlfunction;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-10-24 15:34:18
 * @see org.springrain.selenium.service.Htmlfunction
 */
public interface IHtmlfunctionService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Htmlfunction findHtmlfunctionById(Object id) throws Exception;
	
	
	
}
